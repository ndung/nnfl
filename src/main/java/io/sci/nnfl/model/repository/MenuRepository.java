package io.sci.nnfl.model.repository;

import io.sci.nnfl.model.Menu;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuRepository extends MongoRepository<Menu, String> {
    List<Menu> findAllByParentIdIsNullOrderByOrderIndexAscTitleAsc();
    List<Menu> findAllByParentIdOrderByOrderIndexAscTitleAsc(String parentId);
    List<Menu> findAllByOrderByParentIdAscOrderIndexAscTitleAsc();
}