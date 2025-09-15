package io.sci.nnfl.web;

import io.sci.nnfl.model.GeneralInfo;
import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/general-info")
public class GeneralInfoController extends BaseController {
    private final MaterialRecordService service;

    public GeneralInfoController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody GeneralInfo info) {
        MaterialRecord record = service.getById(materialId);
        if (record.getGeneralInfo() == null) {
            record.setGeneralInfo(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        info.setStage(Stage.values()[stage]);
        if (info.getId() == null || info.getId().isEmpty()) {
            info.setId(UUID.randomUUID().toString());
        }
        record.getGeneralInfo().removeIf(g -> g.getId().equals(info.getId()));
        record.getGeneralInfo().add(info);
        service.save(record);
        String redirect = "/materials/new/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "generalInfo", id);
        return "redirect:/materials/new/" + materialId + "/" + stage;
    }
}

