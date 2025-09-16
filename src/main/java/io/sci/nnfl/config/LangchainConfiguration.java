package io.sci.nnfl.config;

import com.mongodb.client.MongoClient;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.mongodb.IndexMapping;
import dev.langchain4j.store.embedding.mongodb.MongoDbEmbeddingStore;
import io.sci.nnfl.model.repository.MaterialRecordRepository;
import io.sci.nnfl.service.MaterialVectorSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.LinkedHashSet;

@Configuration
@EnableConfigurationProperties({OpenAiEmbeddingProperties.class, MaterialSearchProperties.class})
public class LangchainConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LangchainConfiguration.class);

    @Bean(name = "materialEmbeddingModel")
    @ConditionalOnExpression("!'${openai.api-key:}'.isBlank()")
    public EmbeddingModel materialEmbeddingModel(OpenAiEmbeddingProperties properties) {
        OpenAiEmbeddingModel.OpenAiEmbeddingModelBuilder builder = OpenAiEmbeddingModel.builder()
                .apiKey(properties.getApiKey());

        if (StringUtils.hasText(properties.getBaseUrl())) {
            builder.baseUrl(properties.getBaseUrl());
        }
        if (StringUtils.hasText(properties.getOrganizationId())) {
            builder.organizationId(properties.getOrganizationId());
        }
        if (StringUtils.hasText(properties.getEmbeddingModel())) {
            builder.modelName(properties.getEmbeddingModel());
        }
        if (properties.getTimeout() != null) {
            builder.timeout(properties.getTimeout());
        }
        if (properties.getMaxRetries() != null) {
            builder.maxRetries(properties.getMaxRetries());
        }
        return builder.build();
    }

    @Bean(name = "materialEmbeddingStore")
    @ConditionalOnBean(name = "materialEmbeddingModel")
    @ConditionalOnProperty(prefix = "material-search", name = "enabled", havingValue = "true", matchIfMissing = true)
    public EmbeddingStore<TextSegment> materialEmbeddingStore(
            MongoClient mongoClient,
            MongoTemplate mongoTemplate,
            MaterialSearchProperties properties,
            @Qualifier("materialEmbeddingModel") EmbeddingModel embeddingModel) {

        IndexMapping.IndexMappingBuilder mappingBuilder = IndexMapping.builder()
                .dimension(embeddingModel.dimension());

        if (properties.getMetadataFields() != null && !properties.getMetadataFields().isEmpty()) {
            mappingBuilder.metadataFieldNames(new LinkedHashSet<>(properties.getMetadataFields()));
        }

        MongoDbEmbeddingStore store = MongoDbEmbeddingStore.builder()
                .fromClient(mongoClient)
                .databaseName(mongoTemplate.getDb().getName())
                .collectionName(properties.getCollectionName())
                .indexName(properties.getIndexName())
                .createIndex(properties.isCreateIndex())
                .indexMapping(mappingBuilder.build())
                .build();

        log.info("Material search vector store configured with collection '{}'", properties.getCollectionName());
        return store;
    }

    @Bean
    @ConditionalOnBean(name = {"materialEmbeddingModel", "materialEmbeddingStore"})
    @ConditionalOnProperty(prefix = "material-search", name = "enabled", havingValue = "true", matchIfMissing = true)
    public MaterialVectorSearchService materialVectorSearchService(
            MaterialRecordRepository repository,
            MongoTemplate mongoTemplate,
            MaterialSearchProperties properties,
            @Qualifier("materialEmbeddingModel") EmbeddingModel embeddingModel,
            @Qualifier("materialEmbeddingStore") EmbeddingStore<TextSegment> embeddingStore) {

        log.info("Material vector search service enabled using model '{}'", embeddingModel.getClass().getSimpleName());
        return new MaterialVectorSearchService(repository, mongoTemplate, properties, embeddingModel, embeddingStore);
    }
}
