package com.hrada.oms.dao.model;

import com.hrada.oms.model.model.Equipment;
import com.hrada.oms.model.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shin on 2018/3/30.
 */
public interface ModuleRepository extends JpaRepository<Module, Long> {

    List<Module> findByEquipment(Equipment equipment);
}
