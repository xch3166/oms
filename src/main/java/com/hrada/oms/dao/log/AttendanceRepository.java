package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.AttendanceLog;
import com.hrada.oms.model.model.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by shin on 2018/3/30.
 */
public interface AttendanceRepository extends JpaRepository<AttendanceLog, Long>, JpaSpecificationExecutor<AttendanceLog> {
    Page<AttendanceLog> findAllByPersonal(Pageable pageable, Personal personal);
}
