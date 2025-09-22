package io.sci.nnfl.web;

import io.sci.nnfl.model.IsotopeActivity;
import io.sci.nnfl.model.Material;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/isotope-activity")
public class IsotopeActivityController extends BaseController {
    private final MaterialRecordService service;

    public IsotopeActivityController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody IsotopeActivity activity) {
        Material record = service.getById(materialId);
        if (record.getIsotopeActivities() == null) {
            record.setIsotopeActivities(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        if (activity.getId() == null || activity.getId().isEmpty()) {
            activity.setId(UUID.randomUUID().toString());
            activity.setStage(Stage.values()[stage]);
        }
        record.getIsotopeActivities().removeIf(a -> a.getId().equals(activity.getId()));
        record.getIsotopeActivities().add(activity);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "isotopeActivities", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }
}

