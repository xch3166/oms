package com.hrada.oms.dao.model;

import com.hrada.oms.model.model.Module;
import com.hrada.oms.model.model.Parts;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shin on 2018/3/30.
 */
public interface PartsRepository extends JpaRepository<Parts, Long> {

    List<Parts> findByModules(Module module);
}
