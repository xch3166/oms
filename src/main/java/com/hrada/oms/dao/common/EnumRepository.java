package com.hrada.oms.dao.common;

import com.hrada.oms.model.common.Enum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shin on 2018/8/9.
 */
public interface EnumRepository extends JpaRepository<Enum, Long> {

    List<Enum> findAllByParentIsNull();
    List<Enum> findByParent(Enum e);
    List<Enum> findByParent_Name(String name);
}
