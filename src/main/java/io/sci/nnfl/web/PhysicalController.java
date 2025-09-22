package io.sci.nnfl.web;

import io.sci.nnfl.model.Material;
import io.sci.nnfl.model.Physical;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/physical")
public class PhysicalController extends BaseController {
    private final MaterialRecordService service;

    public PhysicalController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody Physical physical) {
        Material record = service.getById(materialId);
        if (record.getPhysicals() == null) {
            record.setPhysicals(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        sanitizePhysical(physical);
        if (physical.getId() == null || physical.getId().isEmpty()) {
            physical.setId(UUID.randomUUID().toString());
            physical.setStage(Stage.values()[stage]);
        }
        record.getPhysicals().removeIf(p -> p.getId().equals(physical.getId()));
        record.getPhysicals().add(physical);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "physicals", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }

    private void sanitizePhysical(Physical physical) {
        physical.setDensityUnit(trimToNull(physical.getDensityUnit()));
        physical.setStateOfMatter(trimToNull(physical.getStateOfMatter()));
        physical.setMechanicalProperties(trimToNull(physical.getMechanicalProperties()));
        physical.setDescription(trimToNull(physical.getDescription()));
        physical.setDimensions(trimToNull(physical.getDimensions()));
        physical.setCladdingInfo(trimToNull(physical.getCladdingInfo()));
        physical.setCoatingInfo(trimToNull(physical.getCoatingInfo()));
        physical.setAssemblyStructure(trimToNull(physical.getAssemblyStructure()));
        physical.setSurfaceOxideThickness(trimToNull(physical.getSurfaceOxideThickness()));
        physical.setSerialNumbers(trimToNull(physical.getSerialNumbers()));
        physical.setNotes(trimToNull(physical.getNotes()));
    }
}

