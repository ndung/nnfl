package io.sci.nnfl.model.repository;

import io.sci.nnfl.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findAllByParentIsNullOrderByOrderIndexAscTitleAsc();
    List<Menu> findAllByParentOrderByOrderIndexAscTitleAsc(Menu parent);
    List<Menu> findAllByOrderByParentIdAscOrderIndexAscTitleAsc();
}