package io.sci.nnfl.web;

import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.service.MaterialRecordService;
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

    private final MaterialRecordService materialRecordService;
    private final ObjectProvider<MaterialVectorSearchService> searchServiceProvider;

    public SearchController(MMaterialRecordService, MaterialRecordService materialRecordService) {
        this.materialRecordService = materialRecordService;
        this.searchServiceProvider = searchServiceProvider;
    }

    @GetMapping("/search")
    public String showSearch(@RequestParam(value = "q", required = false) String query,
                             @RequestParam(value = "version", required = false) String version,
                             Model model) {
        String sanitizedQuery = query != null ? query.trim() : "";
        String selectedVersion = StringUtils.hasText(version) ? version : "1";
        boolean naturalLanguageRequested = "2".equals(selectedVersion);
        boolean hasQuery = StringUtils.hasText(sanitizedQuery);
        boolean searchEnabled = materialRecordService.isNaturalLanguageSearchEnabled();

        boolean searchAttempted = hasQuery && (!naturalLanguageRequested || searchEnabled);
        List<MaterialRecord> results = Collections.emptyList();

        if (searchAttempted) {
            results = naturalLanguageRequested
                    ? materialRecordService.searchByNaturalLanguage(sanitizedQuery)
                    : materialRecordService.search(sanitizedQuery);
        boolean hasQuery = StringUtils.hasText(sanitizedQuery);
        MaterialVectorSearchService searchService = searchServiceProvider.getIfAvailable();
        boolean searchConfigured = searchService != null;

        List<MaterialSearchResult> results = Collections.emptyList();
        if (hasQuery && searchConfigured) {
            results = searchService.search(sanitizedQuery);
        }

        model.addAttribute("query", sanitizedQuery);
        model.addAttribute("hasQuery", hasQuery);
        model.addAttribute("results", results);
        model.addAttribute("searchEnabled", searchEnabled);
        model.addAttribute("searchAttempted", searchAttempted);
        model.addAttribute("usingNaturalLanguage", naturalLanguageRequested);
        model.addAttribute("version", selectedVersion);
        model.addAttribute("searchConfigured", searchConfigured);
        model.addAttribute("showConfigWarning", hasQuery && !searchConfigured);
        model.addAttribute("results", results);

        return "search";
    }
}
