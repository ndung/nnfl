package io.sci.nnfl.web;

import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.dto.MaterialSearchResult;
import io.sci.nnfl.service.MaterialRecordService;
import io.sci.nnfl.service.MaterialVectorSearchService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class MatchController {

    private final MaterialRecordService materialRecordService;
    private final ObjectProvider<MaterialVectorSearchService> searchServiceProvider;

    public MatchController(ObjectProvider<MaterialVectorSearchService> searchServiceProvider,
                           MaterialRecordService materialRecordService) {
        this.materialRecordService = materialRecordService;
        this.searchServiceProvider = searchServiceProvider;
    }

    @GetMapping("/match")
    public String showMatch(Model model) {
        model.addAttribute("query", "");
        model.addAttribute("hasQuery", false);
        model.addAttribute("hasRecord", false);
        model.addAttribute("results", Collections.emptyList());
        return "match";
    }

    @PostMapping("/match")
    public String executeSearch(@RequestParam(value = "q", required = false) String materialId,
                                Model model) {
        String sanitizedQuery = materialId != null ? materialId.trim() : "";
        boolean hasQuery = StringUtils.hasText(sanitizedQuery);
        List<MaterialSearchResult> results = new ArrayList<>();
        boolean hasRecord = false;
        if (hasQuery) {
            MaterialRecord record = materialRecordService.getById(materialId);
            if (record != null) {
                hasRecord = true;
                MaterialVectorSearchService searchService = searchServiceProvider.getIfAvailable();
                if (searchService != null) {
                    List<MaterialSearchResult> list = searchService.search(record.getData());
                    if (list != null && !list.isEmpty()) {
                        for (MaterialSearchResult result : list) {
                            if (!result.getRecord().getId().equals(record.getId())) {
                                results.add(result);
                            }
                        }
                    }
                }
            }
        }

        model.addAttribute("query", sanitizedQuery);
        model.addAttribute("hasQuery", hasQuery);
        model.addAttribute("results", results);
        model.addAttribute("hasRecord", hasRecord);

        return "match";
    }
}
