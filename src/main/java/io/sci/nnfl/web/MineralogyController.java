package io.sci.nnfl.web;

import io.sci.nnfl.model.Mineralogy;
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
@RequestMapping("/mineralogy")
public class MineralogyController extends BaseController {
    private final MaterialRecordService service;

    public MineralogyController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody Mineralogy mineralogy) {
        MaterialRecord record = service.getById(materialId);
        if (record.getMineralogy() == null) {
            record.setMineralogy(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        mineralogy.setStage(Stage.values()[stage]);
        if (mineralogy.getId() == null || mineralogy.getId().isEmpty()) {
            mineralogy.setId(UUID.randomUUID().toString());
        }
        record.getMineralogy().removeIf(m -> m.getId().equals(mineralogy.getId()));
        record.getMineralogy().add(mineralogy);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "mineralogy", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }
}

