package io.sci.nnfl.service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.dto.MaterialSearchResult;
import io.sci.nnfl.model.repository.MaterialRecordRepository;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class MaterialRecordService extends BaseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialRecordService.class);

    private final MaterialRecordRepository repository;
    private final MongoTemplate template;
    private final MaterialRecordMqlConverter mqlConverter;
    private final ObjectProvider<MaterialVectorSearchService> vectorSearchServiceProvider;

    public MaterialRecordService(MaterialRecordRepository repository,
                                 MongoTemplate template,
                                 ObjectProvider<MaterialVectorSearchService> vectorSearchServiceProvider,
                                 MaterialRecordMqlConverter mqlConverter) {
        this.repository = repository;
        this.template = template;
        this.mqlConverter = mqlConverter;
        this.vectorSearchServiceProvider = vectorSearchServiceProvider;
    }

    @Transactional(readOnly = true)
    public List<MaterialRecord> findAll() {
        return repository.findAll(Sort.by("creationDate").descending());
    }

    @Transactional(readOnly = true)
    public MaterialRecord getById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public MaterialRecord save(MaterialRecord record) {
        MaterialRecord saved = repository.save(record);
        vectorSearchServiceProvider.ifAvailable(service -> service.indexMaterial(saved));
        return saved;
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
        vectorSearchServiceProvider.ifAvailable(service -> service.deleteMaterial(id));
    }

    @Transactional
    public void removeProperty(String materialId, String propertyName, String propertyId) {
        Query q = new Query(Criteria.where("_id").is(materialId)
                .and(propertyName+"._id").is(propertyId));
        Update u = new Update().pull(propertyName,
                new BasicDBObject("_id", propertyId));
        UpdateResult result = template.updateFirst(q, u, MaterialRecord.class);
        if (result.getModifiedCount() > 0) {
            repository.findById(materialId)
                    .ifPresent(updated -> vectorSearchServiceProvider.ifAvailable(service -> service.indexMaterial(updated)));
        }
    }

    @Transactional(readOnly = true)
    public List<MaterialSearchResult> search(String mqlFilter) {
        if (!StringUtils.hasText(mqlFilter)) {
            return Collections.emptyList();
        }
        List<MaterialRecord> list = Collections.emptyList();
        try {
            Document filter = Document.parse(mqlFilter);
            list = template.find(new BasicQuery(filter), MaterialRecord.class);
        } catch (RuntimeException searchError) {
            LOGGER.warn("Failed to execute MaterialRecord search with provided filter: {}", mqlFilter, searchError);
        }
        return buildSearchResult(list);
    }

    @Transactional(readOnly = true)
    public Pair<Optional<String>,List<MaterialSearchResult>> searchByNaturalLanguage(String request) {
        if (!StringUtils.hasText(request) || !mqlConverter.isConfigured()) {
            return Pair.of(Optional.empty(), Collections.emptyList());
        }
        Optional<String> q = mqlConverter.toJsonFilter(request);

        List<MaterialRecord> list = mqlConverter.toQuery(q)
                .map(query -> template.find(query, MaterialRecord.class))
                .orElse(Collections.emptyList());
        List<MaterialSearchResult> materialSearchResults = buildSearchResult(list);
        return Pair.of(q, materialSearchResults);
    }

    private List<MaterialSearchResult> buildSearchResult(List<MaterialRecord> list) {
        List<MaterialSearchResult> result = new ArrayList<>();
        for (MaterialRecord record : list) {
            result.add(new MaterialSearchResult(record, 0.0));
        }
        return result;
    }

    public boolean isNaturalLanguageSearchEnabled() {
        return mqlConverter.isConfigured();
    }
}
