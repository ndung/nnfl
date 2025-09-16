package io.sci.nnfl.web;

import io.sci.nnfl.service.MaterialVectorSearchService;
import io.sci.nnfl.service.MaterialVectorSearchService.MaterialSearchResult;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;

@Controller
public class SearchController {

    private final ObjectProvider<MaterialVectorSearchService> searchServiceProvider;

    public SearchController(ObjectProvider<MaterialVectorSearchService> searchServiceProvider) {
        this.searchServiceProvider = searchServiceProvider;
    }

    @GetMapping("/search")
    public String showSearch(@RequestParam(value = "q", required = false) String query, Model model) {
        String sanitizedQuery = query != null ? query.trim() : "";
        boolean hasQuery = StringUtils.hasText(sanitizedQuery);
        MaterialVectorSearchService searchService = searchServiceProvider.getIfAvailable();
        boolean searchConfigured = searchService != null;

        List<MaterialSearchResult> results = Collections.emptyList();
        if (hasQuery && searchConfigured) {
            results = searchService.search(sanitizedQuery);
        }

        model.addAttribute("query", sanitizedQuery);
        model.addAttribute("hasQuery", hasQuery);
        model.addAttribute("searchConfigured", searchConfigured);
        model.addAttribute("showConfigWarning", hasQuery && !searchConfigured);
        model.addAttribute("results", results);

        return "search";
    }
}
