package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.ServiceLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/3/30.
 */
public interface ServiceRepository extends JpaRepository<ServiceLog, Long> {
}
