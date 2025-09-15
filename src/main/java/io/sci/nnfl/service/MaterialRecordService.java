package io.sci.nnfl.service;

import com.mongodb.BasicDBObject;
import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.repository.MaterialRecordRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaterialRecordService extends BaseService {

    private final MaterialRecordRepository repository;
    private final MongoTemplate template;

    public MaterialRecordService(MaterialRecordRepository repository,
                                 MongoTemplate template) {
        this.repository = repository;
        this.template = template;
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
        return repository.save(record);
    }

    @Transactional
    public void delete(String id) {
        repository.deleteById(id);
    }

    @Transactional
    public long removeChemicalForm(String materialId, String chemicalFormId) {
        Query q = new Query(Criteria.where("_id").is(materialId)
                .and("chemicalForms._id").is(chemicalFormId));
        Update u = new Update().pull("chemicalForms",
                new BasicDBObject("_id", chemicalFormId));
        return template.updateFirst(q, u, MaterialRecord.class)
                .getModifiedCount();
    }
}
