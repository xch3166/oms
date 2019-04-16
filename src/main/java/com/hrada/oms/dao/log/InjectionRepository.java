package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.InjectionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by shin on 2018/3/30.
 */
public interface InjectionRepository extends JpaRepository<InjectionLog, Long>, JpaSpecificationExecutor<InjectionLog> {

    @Query("select sum(i.injection) from InjectionLog i")
    Integer getInjection();

    @Query("select sum(i.solid) from InjectionLog i")
    Integer getSolid();

    @Query("select sum(i.liquid) from InjectionLog i")
    Integer getLiquid();

    List<InjectionLog> findTop15ByOrderByLogDateDesc();
}
