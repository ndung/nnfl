package io.sci.nnfl.web;

import io.sci.nnfl.model.Container;
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
@RequestMapping("/container")
public class ContainerController extends BaseController {
    private final MaterialRecordService service;

    public ContainerController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> save(@PathVariable String materialId,
                                                    @PathVariable Integer stage,
                                                    @RequestBody Container container) {
        Material record = service.getById(materialId);
        if (record.getContainers() == null) {
            record.setContainers(new ArrayList<>());
        }
        if (stage == null || stage < 0 || stage >= Stage.values().length) {
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        sanitizeContainer(container);
        if (container.getId() == null || container.getId().isEmpty()) {
            container.setId(UUID.randomUUID().toString());
            container.setStage(Stage.values()[stage]);
        }
        record.getContainers().removeIf(c -> c.getId().equals(container.getId()));
        record.getContainers().add(container);
        service.save(record);
        String redirect = "/materials/" + materialId + "/" + stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }

    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String delete(@PathVariable String materialId,
                         @PathVariable Integer stage,
                         @PathVariable String id) {
        service.removeProperty(materialId, "containers", id);
        return "redirect:/materials/" + materialId + "/" + stage;
    }

    private void sanitizeContainer(Container container) {
        container.setType(trimToNull(container.getType()));
        container.setVolumeUnit(trimToNull(container.getVolumeUnit()));
        container.setDimensions(trimToNull(container.getDimensions()));
        container.setNotes(trimToNull(container.getNotes()));
    }
}

