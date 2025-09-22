package io.sci.nnfl.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.sci.nnfl.config.FileStorage;
import io.sci.nnfl.model.Material;
import io.sci.nnfl.model.SourceDescription;
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
@RequestMapping("/source-description")
public class SourceDescriptionController extends BaseController {
    private final MaterialRecordService service;
    private final FileStorage storage;
    private final ObjectMapper objectMapper;

    public SourceDescriptionController(MaterialRecordService service,
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
                                                    @RequestBody SourceDescription description) {
        return saveInternal(materialId, stage, description, null);
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
        SourceDescription description;
        try {
            description = objectMapper.readValue(body, SourceDescription.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid payload"));
        }
        return saveInternal(materialId, stage, description, file);
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "sourceDescriptions", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }

    private ResponseEntity<Map<String, Object>> saveInternal(String materialId,
                                                             Integer stageIndex,
                                                             SourceDescription description,
                                                             MultipartFile file) {
        Material record = service.getById(materialId);
        if (record.getSourceDescriptions() == null) {
            record.setSourceDescriptions(new ArrayList<>());
        }
        Optional<Stage> resolvedStage = resolveStage(stageIndex);
        if (resolvedStage.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        Stage stage = resolvedStage.get();
        sanitizeSourceDescription(description);
        if (description.getId() == null || description.getId().isEmpty()) {
            description.setId(UUID.randomUUID().toString());
            description.setStage(stage);
        }
        if (file != null && !file.isEmpty()) {
            try {
                var stored = storage.store(buildStorageKey(materialId, stage, "source-description", description.getId(), file), file);
                description.setImageFile(stored.key());
            } catch (IOException | URISyntaxException e) {
                return ResponseEntity.internalServerError().body(Map.of("ok", false, "error", "Failed to store file"));
            }
        }
        record.getSourceDescriptions().removeIf(s -> s.getId().equals(description.getId()));
        record.getSourceDescriptions().add(description);
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
        return String.format("%s-%s-%s-%s%s",
                materialId,
                stage.name().toLowerCase(Locale.ROOT),
                section,
                entityId,
                extension);
    }

    private void sanitizeSourceDescription(SourceDescription description) {
        description.setSourceType(trimToNull(description.getSourceType()));
        description.setQuantity(trimToNull(description.getQuantity()));
        description.setDescription(trimToNull(description.getDescription()));
        description.setDimensions(trimToNull(description.getDimensions()));
        description.setEncapsulationOrCladding(trimToNull(description.getEncapsulationOrCladding()));
        description.setSerialNumber(trimToNull(description.getSerialNumber()));
        description.setShippingHistory(trimToNull(description.getShippingHistory()));
        description.setReceivingHistory(trimToNull(description.getReceivingHistory()));
        description.setRadiographOrPhotograph(trimToNull(description.getRadiographOrPhotograph()));
        description.setImageFile(trimToNull(description.getImageFile()));
        description.setNotes(trimToNull(description.getNotes()));
    }
}

