package io.sci.nnfl.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.SimpleDateFormat;

@Controller
public class HomeController {


    @GetMapping("/")
    public String root() {
        return "redirect:/admin/users";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

}
