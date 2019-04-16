package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/9/20.
 */
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
