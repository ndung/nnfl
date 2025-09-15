package io.sci.nnfl.web;

import com.google.gson.internal.LinkedTreeMap;
import io.sci.nnfl.model.ChemicalForm;
import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("/materials")
public class MaterialRecordController {

    private final MaterialRecordService service;

    @Getter @Setter
    private String type;

    private Map<String,String> typeOptions() {
        Map<String,String> map = new LinkedTreeMap<>();
        for (int i=0;i<Stage.values().length;i++) {
            map.put(String.valueOf(i),Stage.values()[i].name());
        }
        return map;
    }

    public MaterialRecordController(MaterialRecordService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("materials", service.findAll());
        model.addAttribute("typeOptions", typeOptions());
        return "materials";
    }

    @GetMapping("/new/{materialId}/{stage}")
    public String createForm(Model model, @PathVariable("materialId") String materialId,
                             @PathVariable("stage") String stage) {
        model.addAttribute("material", new MaterialRecord());
        MaterialRecord material = service.getById(materialId);
        model.addAttribute("material", material);
        model.addAttribute("stage", Integer.parseInt(stage));
        return "material-form";
    }

    @PostMapping("/new")
    public String create(Model model) {
        MaterialRecord record = new MaterialRecord();
        record.setCreator(service.getUser());
        record.setCreationDateTime(LocalDateTime.now());
        service.save(record);
        return "redirect:/materials";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") String id, RedirectAttributes ra) {
        service.delete(id);
        return "redirect:/materials";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable("id") String id, RedirectAttributes ra) {
        service.delete(id);
        return "redirect:/materials";
    }

    @PostMapping
    public String create(@ModelAttribute MaterialRecord material,
                         RedirectAttributes ra) {
        service.save(material);
        ra.addFlashAttribute("materialSaved", true);
        return "redirect:/materials";
    }

    @PostMapping("/chemical/{materialId}/{stage}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addChemical(@PathVariable("materialId") String materialId,
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


    @PostMapping("/chemical/{materialId}/{stage}/{id}/delete")
    public String removeChemicalForm(@PathVariable("materialId") String materialId,
                                     @PathVariable("stage") Integer stage,
                                     @PathVariable("id") String id) {
        service.removeChemicalForm(materialId, id);
        return "redirect:/materials/new/"+materialId+"/"+stage;
    }
}
