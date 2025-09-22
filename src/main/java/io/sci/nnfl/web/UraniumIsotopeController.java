package io.sci.nnfl.web;

import io.sci.nnfl.model.IsotopeRatio;
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
@RequestMapping("/uranium-isotope")
public class UraniumIsotopeController extends BaseController {
    private final MaterialRecordService service;

    public UraniumIsotopeController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody IsotopeRatio isotope) {
        Material record = service.getById(materialId);
        if (record.getUraniumIsotopes() == null) {
            record.setUraniumIsotopes(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        if (isotope.getId() == null || isotope.getId().isEmpty()) {
            isotope.setId(UUID.randomUUID().toString());
            isotope.setStage(Stage.values()[stage]);
        }
        record.getUraniumIsotopes().removeIf(i -> i.getId().equals(isotope.getId()));
        record.getUraniumIsotopes().add(isotope);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "uraniumIsotopes", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }
}

