package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.PartsRecord;
import com.hrada.oms.model.model.Parts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/12/21.
 */
public interface PartsRecordRepository extends JpaRepository<PartsRecord, Long> {

    PartsRecord findTopByPartsOrderByIdDesc(Parts parts);

    Page<PartsRecord> findAlllByParts_Id(Pageable pageable, Long id);
}

