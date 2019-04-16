package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.Contract;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by shin on 2018/11/22.
 */
public interface ContractRepository extends JpaRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {

    Page<Contract> findAllByType(Specification specification, Pageable pageable, Integer type);
    List<Contract> findAllByTypeOrderByCreateDateDesc(Integer type);
}
