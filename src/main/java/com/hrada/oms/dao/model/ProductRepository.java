package com.hrada.oms.dao.model;

import com.hrada.oms.model.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2019-03-11.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
