package io.sci.nnfl.web;

import io.sci.nnfl.model.Geology;
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
@RequestMapping("/geology")
public class GeologyController extends BaseController {
    private final MaterialRecordService service;

    public GeologyController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody Geology geology) {
        Material record = service.getById(materialId);
        if (record.getGeology() == null) {
            record.setGeology(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        sanitizeGeology(geology);
        if (geology.getId() == null || geology.getId().isEmpty()) {
            geology.setId(UUID.randomUUID().toString());
            geology.setStage(Stage.values()[stage]);
        }
        record.getGeology().removeIf(g -> g.getId().equals(geology.getId()));
        record.getGeology().add(geology);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "geology", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }

    private void sanitizeGeology(Geology geology) {
        geology.setMineLocation(trimToNull(geology.getMineLocation()));
        geology.setGeologicalFormation(trimToNull(geology.getGeologicalFormation()));
        geology.setDepositTypes(trimToNull(geology.getDepositTypes()));
        geology.setMiningTechnique(trimToNull(geology.getMiningTechnique()));
        geology.setColour(trimToNull(geology.getColour()));
        geology.setNotes(trimToNull(geology.getNotes()));
    }
}

