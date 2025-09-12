package io.sci.nnfl.model.repository;

import io.sci.nnfl.model.MaterialProperty;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository for material property entries.
 */
public interface MaterialPropertyRepository extends MongoRepository<MaterialProperty, String> {
}
