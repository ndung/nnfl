package io.sci.nnfl.model.repository;

import io.sci.nnfl.model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MenuRepository extends MongoRepository<Menu, String> {

}