package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.Leave;
import com.hrada.oms.model.model.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by shin on 2018/10/16.
 */
public interface LeaveRepository extends JpaRepository<Leave, Long>, JpaSpecificationExecutor<Leave> {

    Page<Leave> findAllByApplicant(Pageable pageable, Personal personal);
}
