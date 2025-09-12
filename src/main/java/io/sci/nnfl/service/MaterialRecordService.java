package io.sci.nnfl.service;

import io.sci.nnfl.model.MaterialRecord;
import io.sci.nnfl.model.repository.MaterialRecordRepository;
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

    public MaterialRecordService(MaterialRecordRepository repository) {
        this.repository = repository;
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
}
