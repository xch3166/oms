package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.Seal;
import com.hrada.oms.model.model.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by shin on 2018/10/19.
 */
public interface SealRepository extends JpaRepository<Seal, Long>, JpaSpecificationExecutor<Seal> {

    Page<Seal> findAllByApplicant(Pageable pageable, Personal personal);
}
