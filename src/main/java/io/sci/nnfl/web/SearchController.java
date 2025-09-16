package io.sci.nnfl.web;

import io.sci.nnfl.model.dto.MaterialSearchResult;
import io.sci.nnfl.service.MaterialRecordService;
import io.sci.nnfl.service.MaterialVectorSearchService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    private final MaterialRecordService materialRecordService;
    private final ObjectProvider<MaterialVectorSearchService> searchServiceProvider;

    public SearchController(ObjectProvider<MaterialVectorSearchService> searchServiceProvider,
                            MaterialRecordService materialRecordService) {
        this.materialRecordService = materialRecordService;
        this.searchServiceProvider = searchServiceProvider;
    }

    @GetMapping("/search")
    public String showSearch(Model model) {
        model.addAttribute("query", "");
        model.addAttribute("hasQuery", false);
        model.addAttribute("results", Collections.emptyList());
        model.addAttribute("version", "1");
        model.addAttribute("mql", null);

        return "search";
    }

    @PostMapping("/search")
    public String executeSearch(@RequestParam(value = "q", required = false) String query,
                                @RequestParam(value = "v", required = false) String version,
                                Model model) {
        String sanitizedQuery = query != null ? query.trim() : "";
        String sanitizedVersion = StringUtils.hasText(version) ? version.trim() : "1";
        boolean hasQuery = StringUtils.hasText(sanitizedQuery);
        List<MaterialSearchResult> results = Collections.emptyList();
        String generatedMql = null;

        if (hasQuery) {
            if ("1".equals(sanitizedVersion)) {
                MaterialVectorSearchService searchService = searchServiceProvider.getIfAvailable();
                if (searchService != null) {
                    results = searchService.search(sanitizedQuery);
                }
            } else if ("2".equals(sanitizedVersion)) {
                boolean searchEnabled = materialRecordService.isNaturalLanguageSearchEnabled();
                if (searchEnabled) {
                    Pair<Optional<String>, List<MaterialSearchResult>> pair = materialRecordService.searchByNaturalLanguage(sanitizedQuery);
                    Optional<String> opt = pair.getFirst();
                    results = pair.getSecond();
                    generatedMql = opt.orElse(null);
                }
            } else {
                results = materialRecordService.search(sanitizedQuery);
            }
        }

        model.addAttribute("query", sanitizedQuery);
        model.addAttribute("hasQuery", hasQuery);
        model.addAttribute("results", results);
        model.addAttribute("version", sanitizedVersion);
        model.addAttribute("mql", generatedMql);

        return "search";
    }
}
