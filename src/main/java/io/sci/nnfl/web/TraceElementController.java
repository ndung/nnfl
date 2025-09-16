package io.sci.nnfl.web;

import io.sci.nnfl.model.Element;
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
@RequestMapping("/trace-element")
public class TraceElementController extends BaseController {
    private final MaterialRecordService service;

    public TraceElementController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody Element element) {
        MaterialRecord record = service.getById(materialId);
        if (record.getTraceElements() == null) {
            record.setTraceElements(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        element.setStage(Stage.values()[stage]);
        if (element.getId() == null || element.getId().isEmpty()) {
            element.setId(UUID.randomUUID().toString());
        }
        record.getTraceElements().removeIf(e -> e.getId().equals(element.getId()));
        record.getTraceElements().add(element);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "traceElements", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }
}

