package io.sci.nnfl.web;

import io.sci.nnfl.model.ChemicalForm;
import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/chemical-form")
public class ChemicalFormController extends BaseController{
    private final MaterialRecordService service;

    public ChemicalFormController(MaterialRecordService service) {
        this.service = service;
    }

    @PostMapping("/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> saveForm(@PathVariable("materialId") String materialId,
                                                        @PathVariable("stage") Integer stage,
                                                        @RequestBody ChemicalForm chemicalForm) {
        MaterialRecord record = service.getById(materialId);
        if (record.getChemicalForms() == null) {
            record.setChemicalForms(new ArrayList<>());
        }
        if (stage==null || stage < 0 || stage>10){
            return ResponseEntity.badRequest().body(Map.of("ok", false, "error", "Invalid stage"));
        }
        chemicalForm.setStage(Stage.values()[stage]);
        if (chemicalForm.getId() == null || chemicalForm.getId().isEmpty()) {
            chemicalForm.setId(UUID.randomUUID().toString());
            record.getChemicalForms().add(chemicalForm);
        } else {
            Optional<ChemicalForm> existing = record.getChemicalForms().stream()
                    .filter(cf -> cf.getId().equals(chemicalForm.getId()))
                    .findFirst();
            if (existing.isPresent()) {
                ChemicalForm cf = existing.get();
                cf.setStage(chemicalForm.getStage());
                cf.setCompoundName(chemicalForm.getCompoundName());
                cf.setStoichiometryDeviation(chemicalForm.getStoichiometryDeviation());
                cf.setNotes(chemicalForm.getNotes());
            } else {
                record.getChemicalForms().add(chemicalForm);
            }
        }
        service.save(record);
        String redirect =  "/materials/new/"+materialId+"/"+stage;
        return ResponseEntity.ok(Map.of("ok", true, "redirectUrl", redirect));
    }


    @PostMapping("/{materialId}/{stage}/{id}/delete")
    public String removeChemicalForm(@PathVariable("materialId") String materialId,
                                     @PathVariable("stage") Integer stage,
                                     @PathVariable("id") String id) {
        service.removeChemicalForm(materialId, id);
        return "redirect:/materials/new/"+materialId+"/"+stage;
    }
}
