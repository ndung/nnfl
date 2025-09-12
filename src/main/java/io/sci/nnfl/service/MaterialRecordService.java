package io.sci.nnfl.service;

import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.repository.MaterialRecordRepository;
import io.sci.nnfl.model.repository.MaterialPropertyRepository;
import io.sci.nnfl.model.MaterialProperty;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaterialRecordService extends BaseService {

    private final MaterialRecordRepository repository;
    private final MaterialPropertyRepository propertyRepository;

    public MaterialRecordService(MaterialRecordRepository repository,
                                 MaterialPropertyRepository propertyRepository) {
        this.repository = repository;
        this.propertyRepository = propertyRepository;
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
    public void saveProperty(String type, java.util.List<String> values) {
        MaterialProperty property = MaterialProperty.builder()
                .type(type)
                .values(values)
                .build();
        propertyRepository.save(property);
    }
}
