package com.hrada.oms.dao.model;

import com.hrada.oms.model.model.Base;
import com.hrada.oms.model.model.Well;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shin on 2018/3/30.
 */
public interface WellRepository extends JpaRepository<Well, Long> {

    List<Well> findWellsByBase(Base base);
}
