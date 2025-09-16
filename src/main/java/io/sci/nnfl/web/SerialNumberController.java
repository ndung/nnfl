package io.sci.nnfl.web;

import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.SerialNumber;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/serial-number")
public class SerialNumberController extends BaseController {
    private final MaterialRecordService service;

    public SerialNumberController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody SerialNumber serial) {
        MaterialRecord record = service.getById(materialId);
        if (record.getSerialNumbers() == null) {
            record.setSerialNumbers(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        serial.setStage(Stage.values()[stage]);
        if (serial.getId() == null || serial.getId().isEmpty()) {
            serial.setId(UUID.randomUUID().toString());
        }
        record.getSerialNumbers().removeIf(s -> s.getId().equals(serial.getId()));
        record.getSerialNumbers().add(serial);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "serialNumbers", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }
}

