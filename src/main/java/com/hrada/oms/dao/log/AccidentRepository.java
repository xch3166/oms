package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.Accident;
import com.hrada.oms.model.model.Base;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/3/30.
 */
public interface AccidentRepository extends JpaRepository<Accident, Long> {

    Accident findTopByBaseOrderByLogDateDesc(Base base);
}
