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
@RequestMapping("/stable-isotope")
public class StableIsotopeController extends BaseController {
    private final MaterialRecordService service;

    public StableIsotopeController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody IsotopeRatio isotope) {
        Material record = service.getById(materialId);
        if (record.getStableIsotopes() == null) {
            record.setStableIsotopes(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        sanitizeIsotopeRatio(isotope);
        if (isotope.getId() == null || isotope.getId().isEmpty()) {
            isotope.setId(UUID.randomUUID().toString());
            isotope.setStage(Stage.values()[stage]);
        }
        record.getStableIsotopes().removeIf(i -> i.getId().equals(isotope.getId()));
        record.getStableIsotopes().add(isotope);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "stableIsotopes", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }

    private void sanitizeIsotopeRatio(IsotopeRatio isotope) {
        isotope.setName(trimToNull(isotope.getName()));
        isotope.setUnit(trimToNull(isotope.getUnit()));
        isotope.setNotes(trimToNull(isotope.getNotes()));
    }
}

