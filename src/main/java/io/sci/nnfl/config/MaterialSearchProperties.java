package io.sci.nnfl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.LinkedHashSet;
import java.util.Set;

@ConfigurationProperties(prefix = "material-search")
public class MaterialSearchProperties {

    private boolean enabled = true;
    private String collectionName = "material_embeddings";
    private String indexName = "material_embeddings_index";
    private boolean createIndex = true;
    private int maxResults = 5;
    private double minScore = 0.0;
    private Set<String> metadataFields = new LinkedHashSet<>(Set.of("materialId", "state", "createdAt", "notes"));

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean isCreateIndex() {
        return createIndex;
    }

    public void setCreateIndex(boolean createIndex) {
        this.createIndex = createIndex;
    }

    public int getMaxResults() {
        return maxResults;
    }

    public void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    public double getMinScore() {
        return minScore;
    }

    public void setMinScore(double minScore) {
        this.minScore = minScore;
    }

    public Set<String> getMetadataFields() {
        return metadataFields;
    }

    public void setMetadataFields(Set<String> metadataFields) {
        if (metadataFields == null || metadataFields.isEmpty()) {
            this.metadataFields = new LinkedHashSet<>();
        } else {
            this.metadataFields = new LinkedHashSet<>(metadataFields);
        }
    }
}
