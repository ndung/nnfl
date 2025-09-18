package io.sci.nnfl.web;

import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.Morphology;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/morphology")
public class MorphologyController extends BaseController {
    private final MaterialRecordService service;

    public MorphologyController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody Morphology morphology) {
        MaterialRecord record = service.getById(materialId);
        if (record.getMorphologies() == null) {
            record.setMorphologies(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        if (morphology.getId() == null || morphology.getId().isEmpty()) {
            morphology.setId(UUID.randomUUID().toString());
            morphology.setStage(Stage.values()[stage]);
        }
        record.getMorphologies().removeIf(m -> m.getId().equals(morphology.getId()));
        record.getMorphologies().add(morphology);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "morphologies", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }
}

