package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.ContractDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by shin on 2019-03-15.
 */
public interface ContractDetailRepository extends JpaRepository<ContractDetail, Long>, JpaSpecificationExecutor<ContractDetail> {
}
