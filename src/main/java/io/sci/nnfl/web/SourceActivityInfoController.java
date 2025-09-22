package io.sci.nnfl.web;

import io.sci.nnfl.model.Material;
import io.sci.nnfl.model.SourceActivityInfo;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/source-activity-info")
public class SourceActivityInfoController extends BaseController {
    private final MaterialRecordService service;

    public SourceActivityInfoController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody SourceActivityInfo info) {
        Material record = service.getById(materialId);
        if (record.getSourceActivityInfo() == null) {
            record.setSourceActivityInfo(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        sanitizeSourceActivityInfo(info);
        if (info.getId() == null || info.getId().isEmpty()) {
            info.setId(UUID.randomUUID().toString());
            info.setStage(Stage.values()[stage]);
        }
        record.getSourceActivityInfo().removeIf(s -> s.getId().equals(info.getId()));
        record.getSourceActivityInfo().add(info);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "sourceActivityInfo", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }

    private void sanitizeSourceActivityInfo(SourceActivityInfo info) {
        info.setNotes(trimToNull(info.getNotes()));
    }
}

