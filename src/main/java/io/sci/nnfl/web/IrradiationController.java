package io.sci.nnfl.web;

import io.sci.nnfl.model.IrradiationHistory;
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
@RequestMapping("/irradiation")
public class IrradiationController extends BaseController {
    private final MaterialRecordService service;

    public IrradiationController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody IrradiationHistory irradiation) {
        MaterialRecord record = service.getById(materialId);
        if (record.getIrradiationHistories() == null) {
            record.setIrradiationHistories(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        irradiation.setStage(Stage.values()[stage]);
        if (irradiation.getId() == null || irradiation.getId().isEmpty()) {
            irradiation.setId(UUID.randomUUID().toString());
        }
        record.getIrradiationHistories().removeIf(i -> i.getId().equals(irradiation.getId()));
        record.getIrradiationHistories().add(irradiation);
        service.save(record);
        String redirect = "/materials/new/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "irradiationHistories", id);
        return "redirect:/materials/new/" + materialId + "/" + stage;
    }
}

