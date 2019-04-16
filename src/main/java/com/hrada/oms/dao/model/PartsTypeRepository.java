package com.hrada.oms.dao.model;

import com.hrada.oms.model.model.PartsType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shin on 2018/3/30.
 */
public interface PartsTypeRepository extends JpaRepository<PartsType, Long> {
    List<PartsType> findAllByParentIsNull();
    List<PartsType> findByParent(PartsType partsType);
}
