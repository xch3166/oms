package com.hrada.oms.dao.model;

import com.hrada.oms.model.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shin on 2019-03-11.
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

    List<ProductCategory> findAllByParentIsNull();

    List<ProductCategory> findByParent(ProductCategory productCategory);
}
