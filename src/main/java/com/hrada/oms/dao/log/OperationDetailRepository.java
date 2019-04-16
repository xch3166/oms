package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.OperationDetail;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/3/30.
 */
public interface OperationDetailRepository extends JpaRepository<OperationDetail, Long> {

}
