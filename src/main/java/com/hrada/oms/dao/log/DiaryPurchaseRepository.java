package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.DiaryPurchase;
import com.hrada.oms.model.model.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/10/19.
 */
public interface DiaryPurchaseRepository extends JpaRepository<DiaryPurchase, Long> {

    Page<DiaryPurchase> findAllByApplicant(Pageable pageable, Personal personal);
}
