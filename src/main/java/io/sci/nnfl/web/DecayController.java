package io.sci.nnfl.web;

import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.model.UraniumDecaySeriesRadionuclide;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/decay")
public class DecayController extends BaseController {
    private final MaterialRecordService service;

    public DecayController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody UraniumDecaySeriesRadionuclide decay) {
        MaterialRecord record = service.getById(materialId);
        if (record.getUraniumDecaySeriesRadionuclides() == null) {
            record.setUraniumDecaySeriesRadionuclides(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        if (decay.getId() == null || decay.getId().isEmpty()) {
            decay.setId(UUID.randomUUID().toString());
            decay.setStage(Stage.values()[stage]);
        }
        record.getUraniumDecaySeriesRadionuclides().removeIf(d -> d.getId().equals(decay.getId()));
        record.getUraniumDecaySeriesRadionuclides().add(decay);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "uraniumDecaySeriesRadionuclides", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }
}

