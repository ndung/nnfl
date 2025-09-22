package io.sci.nnfl.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sci.nnfl.config.FileStorage;
import io.sci.nnfl.model.Material;
import io.sci.nnfl.model.Morphology;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/morphology")
public class MorphologyController extends BaseController {
    private final MaterialRecordService service;
    private final FileStorage storage;
    private final ObjectMapper objectMapper;

    public MorphologyController(MaterialRecordService service,
                                FileStorage storage,
                                ObjectMapper objectMapper) {
        this.service = service;
        this.storage = storage;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/{materialId}/{stage}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody Morphology morphology) {
        return saveInternal(materialId, stage, morphology, null);
    }

    @PostMapping(value = "/{materialId}/{stage}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveWithFile(@PathVariable String materialId,
                                                            @PathVariable Integer stage,
                                                            @RequestPart(value = "payload", required = false) String payload,
                                                            @RequestPart(value = "data", required = false) String data,
                                                            @RequestPart(value = "file", required = false) MultipartFile file) {
        String body = payload != null ? payload : data;
        if (body == null) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Missing payload"));
        }
        Morphology morphology;
        try {
            morphology = objectMapper.readValue(body, Morphology.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid payload"));
        }
        return saveInternal(materialId, stage, morphology, file);
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "morphologies", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }

    private ResponseEntity<Map<String, Object>> saveInternal(String materialId,
                                                             Integer stageIndex,
                                                             Morphology morphology,
                                                             MultipartFile file) {
        Material record = service.getById(materialId);
        if (record.getMorphologies() == null) {
            record.setMorphologies(new ArrayList<>());
        }
        Optional<Stage> resolvedStage = resolveStage(stageIndex);
        if (resolvedStage.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        Stage stage = resolvedStage.get();
        if (morphology.getId() == null || morphology.getId().isEmpty()) {
            morphology.setId(UUID.randomUUID().toString());
            morphology.setStage(stage);
        }
        if (file != null && !file.isEmpty()) {
            try {
                var stored = storage.store(buildStorageKey(materialId, stage, "morphology", morphology.getId(), file), file);
                morphology.setImageFile(stored.key());
            } catch (IOException | URISyntaxException e) {
                return ResponseEntity.internalServerError().body(Map.of("ok", false, "error", "Failed to store file"));
            }
        }
        record.getMorphologies().removeIf(m -> m.getId().equals(morphology.getId()));
        record.getMorphologies().add(morphology);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stageIndex;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    private Optional<Stage> resolveStage(Integer stageIndex) {
        if (stageIndex == null || stageIndex < 0 || stageIndex >= Stage.values().length) {
            return Optional.empty();
        }
        return Optional.of(Stage.values()[stageIndex]);
    }

    private String buildStorageKey(String materialId,
                                   Stage stage,
                                   String section,
                                   String entityId,
                                   MultipartFile file) {
        String originalFilename = Optional.ofNullable(file.getOriginalFilename()).orElse("file");
        int dot = originalFilename.lastIndexOf('.');
        String extension = dot >= 0 ? originalFilename.substring(dot) : "";
        return String.format("materials/%s/%s/%s/%s%s",
                materialId,
                stage.name().toLowerCase(Locale.ROOT),
                section,
                entityId,
                extension);
    }
}

