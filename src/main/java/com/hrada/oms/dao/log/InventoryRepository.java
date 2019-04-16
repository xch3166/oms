package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.Inventory;
import com.hrada.oms.model.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2019-03-13.
 */
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Inventory findByProduct(Product product);
}
