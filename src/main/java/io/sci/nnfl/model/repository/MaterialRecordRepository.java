package io.sci.nnfl.model.repository;

import io.sci.nnfl.model.MaterialRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for {@link MaterialRecord} documents.
 */
public interface MaterialRecordRepository extends MongoRepository<MaterialRecord, String> {
}

