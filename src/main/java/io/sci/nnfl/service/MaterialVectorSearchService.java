package io.sci.nnfl.service;

import com.mongodb.client.result.DeleteResult;
import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import io.sci.nnfl.config.MaterialSearchProperties;
import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.repository.MaterialRecordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import dev.langchain4j.exception.UnsupportedFeatureException;

public class MaterialVectorSearchService {

    private static final Logger log = LoggerFactory.getLogger(MaterialVectorSearchService.class);
    private static final int DEFAULT_SNIPPET_LENGTH = 200;

    private final MaterialRecordRepository repository;
    private final MongoTemplate mongoTemplate;
    private final MaterialSearchProperties properties;
    private final EmbeddingModel embeddingModel;
    private final EmbeddingStore<TextSegment> embeddingStore;

    public MaterialVectorSearchService(MaterialRecordRepository repository,
                                       MongoTemplate mongoTemplate,
                                       MaterialSearchProperties properties,
                                       EmbeddingModel embeddingModel,
                                       EmbeddingStore<TextSegment> embeddingStore) {
        this.repository = repository;
        this.mongoTemplate = mongoTemplate;
        this.properties = properties;
        this.embeddingModel = embeddingModel;
        this.embeddingStore = embeddingStore;
    }

    public void indexMaterial(MaterialRecord record) {
        if (record == null || !StringUtils.hasText(record.getId())) {
            return;
        }
        try {
            TextSegment segment = buildSegment(record);
            if (segment == null) {
                deleteMaterial(record.getId());
                return;
            }
            Embedding embedding = embeddingModel.embed(segment).content();
            if (embedding == null) {
                log.warn("Embedding model returned null content for material {}", record.getId());
                return;
            }
            deleteMaterial(record.getId());
            embeddingStore.addAll(List.of(record.getId()), List.of(embedding), List.of(segment));
            embeddingStore.add(record.getId(), embedding, segment);
        } catch (Exception ex) {
            log.error("Failed to index material {}", record.getId(), ex);
        }
    }

    public void deleteMaterial(String materialId) {
        if (!StringUtils.hasText(materialId)) {
            return;
        }
        try {
            embeddingStore.remove(materialId);
        } catch (UnsupportedFeatureException ex) {
            log.warn("Embedding store does not support removal; embeddings for material {} may persist", materialId);
        } catch (Exception ex) {
            log.error("Failed to delete embedding for material {}", materialId, ex);
        }
    }

    public List<MaterialSearchResult> search(String query) {
        if (!StringUtils.hasText(query)) {
            return Collections.emptyList();
        }
        try {
            Embedding queryEmbedding = embeddingModel.embed(query).content();
            if (queryEmbedding == null) {
                return Collections.emptyList();
            }
            double minScore = Math.max(properties.getMinScore(), 0.0);
            if (minScore > 1.0) {
                minScore = 1.0;
            }
            EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                    .queryEmbedding(queryEmbedding)
                    .maxResults(Math.max(properties.getMaxResults(), 1))
                    .minScore(minScore)
                    .build();
            EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(request);
            if (searchResult == null || searchResult.matches() == null || searchResult.matches().isEmpty()) {
                return Collections.emptyList();
            }
            List<EmbeddingMatch<TextSegment>> matches = searchResult.matches();
            List<String> materialIds = matches.stream()
                    .map(EmbeddingMatch::embeddingId)
            List<EmbeddingMatch<TextSegment>> matches = embeddingStore.findRelevant(
                    queryEmbedding,
                    Math.max(properties.getMaxResults(), 1),
                    Math.max(properties.getMinScore(), 0.0));
            if (matches == null || matches.isEmpty()) {
                return Collections.emptyList();
            }
            List<String> materialIds = matches.stream()
                    .map(EmbeddingMatch::id)
                    .filter(StringUtils::hasText)
                    .toList();
            Map<String, MaterialRecord> materialsById = repository.findAllById(materialIds).stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(MaterialRecord::getId, Function.identity()));

            List<MaterialSearchResult> results = new ArrayList<>();
            for (EmbeddingMatch<TextSegment> match : matches) {
                String materialId = match.embeddingId();
                String materialId = match.id();
                double score = match.score() != null ? match.score() : 0.0;
                MaterialRecord material = materialId != null ? materialsById.get(materialId) : null;
                TextSegment segment = match.embedded();
                results.add(new MaterialSearchResult(
                        materialId,
                        score,
                        buildTitle(materialId, material, segment),
                        buildSnippet(material, segment)
                ));
            }
            return results;
        } catch (Exception ex) {
            log.error("Material search failed", ex);
            return Collections.emptyList();
        }
    }

    public void deleteMaterial(String materialId) {
        if (!StringUtils.hasText(materialId)) {
            return;
        }
        try {
            DeleteResult result = mongoTemplate.remove(Query.query(Criteria.where("_id").is(materialId)),
                    properties.getCollectionName());
            if (result.wasAcknowledged() && result.getDeletedCount() > 0) {
                log.debug("Removed embedding for material {}", materialId);
            }
        } catch (Exception ex) {
            log.error("Failed to delete embedding for material {}", materialId, ex);
        }
    }

    private TextSegment buildSegment(MaterialRecord record) {
        String content = record.getData();
        if (!StringUtils.hasText(content)) {
            return null;
        }
        Metadata metadata = Metadata.from("materialId", record.getId());
        if (StringUtils.hasText(record.getState())) {
            metadata.put("state", record.getState());
        }
        if (record.getCreationDateTime() != null) {
            metadata.put("createdAt", DateTimeFormatter.ISO_INSTANT.format(record.getCreationDateTime().toInstant()));
        }
        if (StringUtils.hasText(record.getNotes())) {
            metadata.put("notes", abbreviate(record.getNotes(), DEFAULT_SNIPPET_LENGTH));
        }
        return TextSegment.from(content, metadata);
    }

    private String buildTitle(String materialId, MaterialRecord material, TextSegment segment) {
        if (material != null && StringUtils.hasText(material.getState())) {
            return material.getState() + " — " + materialId;
        }
        if (segment != null && segment.metadata() != null) {
            String state = segment.metadata().getString("state");
            if (StringUtils.hasText(state) && StringUtils.hasText(materialId)) {
                return state + " — " + materialId;
            }
        }
        if (StringUtils.hasText(materialId)) {
            return "Material " + materialId;
        }
        return "Material";
    }

    private String buildSnippet(MaterialRecord material, TextSegment segment) {
        String snippet = null;
        if (segment != null && segment.metadata() != null) {
            snippet = segment.metadata().getString("notes");
        }
        if (!StringUtils.hasText(snippet) && material != null && StringUtils.hasText(material.getNotes())) {
            snippet = material.getNotes();
        }
        if (!StringUtils.hasText(snippet) && segment != null) {
            snippet = segment.text();
        }
        return abbreviate(snippet, DEFAULT_SNIPPET_LENGTH);
    }

    private String abbreviate(String value, int maxLength) {
        if (!StringUtils.hasText(value)) {
            return "";
        }
        String normalized = value.replaceAll("\\s+", " ").trim();
        if (normalized.length() <= maxLength) {
            return normalized;
        }
        return normalized.substring(0, Math.max(0, maxLength - 3)) + "...";
    }

    public static final class MaterialSearchResult {
        private final String materialId;
        private final double score;
        private final String title;
        private final String snippet;

        public MaterialSearchResult(String materialId, double score, String title, String snippet) {
            this.materialId = materialId;
            this.score = score;
            this.title = title;
            this.snippet = snippet;
        }

        public String getMaterialId() {
            return materialId;
        }

        public double getScore() {
            return score;
        }

        public String getTitle() {
            return title;
        }

        public String getSnippet() {
            return snippet;
        }

        public String getScoreDisplay() {
            return String.format(Locale.US, "%.3f", score);
        }

        public boolean hasSnippet() {
            return snippet != null && !snippet.isBlank();
        }
    }
}
