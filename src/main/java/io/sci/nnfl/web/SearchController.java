package io.sci.nnfl.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;

@Controller
public class SearchController {

    @GetMapping("/search")
    public String showSearch(@RequestParam(value = "q", required = false) String query, Model model) {
        String sanitizedQuery = query != null ? query.trim() : "";
        boolean hasQuery = !sanitizedQuery.isEmpty();

        model.addAttribute("query", sanitizedQuery);
        model.addAttribute("hasQuery", hasQuery);
        model.addAttribute("results", Collections.emptyList());

        return "search";
    }
}
