package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.GDN;
import com.hrada.oms.model.log.Inventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shin on 2018/7/30.
 */
public interface GDNRepository extends JpaRepository<GDN, Long> {
    Page<GDN> findAllByInventories(Pageable pageable, List<Inventory> inventories);
}
