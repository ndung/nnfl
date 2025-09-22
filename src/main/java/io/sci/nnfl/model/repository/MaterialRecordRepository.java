package io.sci.nnfl.model.repository;

import io.sci.nnfl.model.Material;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for {@link Material} documents.
 */
public interface MaterialRecordRepository extends MongoRepository<Material, String> {

}

