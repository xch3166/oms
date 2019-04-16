package com.hrada.oms.dao.common;

import com.hrada.oms.model.common.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/11/2.
 */
public interface LogRepository extends JpaRepository<Log, Long> {
}
