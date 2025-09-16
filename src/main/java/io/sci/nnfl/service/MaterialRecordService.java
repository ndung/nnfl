package io.sci.nnfl.service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.result.UpdateResult;
import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.repository.MaterialRecordRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MaterialRecordService extends BaseService {

    private final MaterialRecordRepository repository;
    private final MongoTemplate template;
    private final ObjectProvider<MaterialVectorSearchService> vectorSearchServiceProvider;

    public MaterialRecordService(MaterialRecordRepository repository,
                                 MongoTemplate template,
                                 ObjectProvider<MaterialVectorSearchService> vectorSearchServiceProvider) {
        this.repository = repository;
        this.template = template;
        this.vectorSearchServiceProvider = vectorSearchServiceProvider;
    }

    @Transactional(readOnly = true)
    public List<MaterialRecord> findAll() {
        return repository.findAll(Sort.by("creationDate").descending());
    }

    @Transactional(readOnly = true)
    public MaterialRecord getById(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
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
}
