package io.sci.nnfl.web;

import io.sci.nnfl.model.IsotopeRatio;
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
@RequestMapping("/plutonium-isotope")
public class PlutoniumIsotopeController extends BaseController {
    private final MaterialRecordService service;

    public PlutoniumIsotopeController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody IsotopeRatio isotope) {
        MaterialRecord record = service.getById(materialId);
        if (record.getPlutoniumIsotopes() == null) {
            record.setPlutoniumIsotopes(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        isotope.setStage(Stage.values()[stage]);
        if (isotope.getId() == null || isotope.getId().isEmpty()) {
            isotope.setId(UUID.randomUUID().toString());
        }
        record.getPlutoniumIsotopes().removeIf(i -> i.getId().equals(isotope.getId()));
        record.getPlutoniumIsotopes().add(isotope);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "plutoniumIsotopes", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }
}

