package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.ProjectDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by shin on 2018/12/7.
 */
public interface ProjectDetailRepository extends JpaRepository<ProjectDetail, Long> {

    List<ProjectDetail> findAllBySupplier_Id(Long id);
}
