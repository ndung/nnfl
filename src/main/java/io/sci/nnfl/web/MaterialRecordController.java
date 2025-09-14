package io.sci.nnfl.web;

import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.model.repository.MaterialRecordRepository;
import io.sci.nnfl.service.MaterialRecordService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/materials")
public class MaterialRecordController {

    private final MaterialRecordService service;
    private final MaterialRecordRepository materialRecordRepository;

    @Getter @Setter
    private String type;

    private Map<String,String> typeOptions() {
        Map<String,String> map = new TreeMap<>();
        for (int i=0;i<Stage.values().length;i++) {
            map.put(Stage.values()[i].name(),Stage.values()[i].name());
        }
        return map;
    }

    public MaterialRecordController(MaterialRecordService service, MaterialRecordRepository materialRecordRepository) {
        this.service = service;
        this.materialRecordRepository = materialRecordRepository;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("materials", service.findAll());
        model.addAttribute("typeOptions", typeOptions());
        return "materials";
    }

    @GetMapping("/new/{id}/{stage}")
    public String createForm(Model model, @PathVariable("id") String id, @PathVariable("stage") String stage) {
        model.addAttribute("material", new MaterialRecord());
        materialRecordRepository.findById(id).ifPresent(materialRecord -> {
            model.addAttribute("material", materialRecord);
        });
        model.addAttribute("stage", stage);
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
}
