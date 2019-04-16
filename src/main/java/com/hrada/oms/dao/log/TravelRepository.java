package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.Travel;
import com.hrada.oms.model.model.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by shin on 2018/10/24.
 */
public interface TravelRepository extends JpaRepository<Travel, Long> {

    Page<Travel> findAllByApplicant(Pageable pageable, Personal personal);
}
