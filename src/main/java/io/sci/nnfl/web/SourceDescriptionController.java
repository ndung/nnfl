package io.sci.nnfl.web;

import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.SourceDescription;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/source-description")
public class SourceDescriptionController extends BaseController {
    private final MaterialRecordService service;

    public SourceDescriptionController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody SourceDescription description) {
        MaterialRecord record = service.getById(materialId);
        if (record.getSourceDescriptions() == null) {
            record.setSourceDescriptions(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        description.setStage(Stage.values()[stage]);
        if (description.getId() == null || description.getId().isEmpty()) {
            description.setId(UUID.randomUUID().toString());
        }
        record.getSourceDescriptions().removeIf(s -> s.getId().equals(description.getId()));
        record.getSourceDescriptions().add(description);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "sourceDescriptions", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }
}

