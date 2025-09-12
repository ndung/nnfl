package io.sci.nnfl.web;

import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.service.MaterialRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/materials")
public class MaterialRecordController {

    private final MaterialRecordService service;

    public MaterialRecordController(MaterialRecordService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("materials", service.findAll());
        return "materials";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("material", new MaterialRecord());
        return "material-form";
    }

    @PostMapping
    public String create(@ModelAttribute MaterialRecord material,
                         RedirectAttributes ra) {
        service.save(material);
        ra.addFlashAttribute("materialSaved", true);
        return "redirect:/materials";
    }
}
