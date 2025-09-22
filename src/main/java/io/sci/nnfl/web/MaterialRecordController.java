package io.sci.nnfl.web;

import com.google.gson.internal.LinkedTreeMap;
import io.sci.nnfl.model.Material;
import io.sci.nnfl.model.Stage;
import io.sci.nnfl.service.MaterialRecordService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/{materialId}/{stage}")
    public String createForm(Model model, @PathVariable("materialId") String materialId,
                             @PathVariable("stage") String stage) {
        model.addAttribute("material", new Material());
        Material material = service.getById(materialId);
        model.addAttribute("material", material);
        model.addAttribute("stage", Integer.parseInt(stage));
        return "material-form";
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
    public String create(@ModelAttribute Material material,
                         RedirectAttributes ra) {
        Material record = service.getById(material.getId());
        if (record == null) {
            material.setCreator(service.getUser());
            material.setCreationDateTime(new Date());
            service.save(material);
            ra.addFlashAttribute("materialSaved", true);
        }else{
            ra.addFlashAttribute("materialExist", true);
        }
        return "redirect:/materials";
    }

}
