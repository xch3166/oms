package com.hrada.oms.dao.log;

import com.hrada.oms.model.log.Payment;
import com.hrada.oms.model.model.Personal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by shin on 2018/10/16.
 */
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    Page<Payment> findAllByApplicant(Pageable pageable, Personal personal);

    @Query("select sum(i.amount) from Payment i where i.type=0 and i.currency=0 and YEAR(i.logDate)=?1")
    Double getPurchaseRMBByYear(Integer year);

    @Query("select sum(i.amount) from Payment i where i.type=0 and i.currency=1 and YEAR(i.logDate)=?1")
    Double getPurchaseUSDByYear(Integer year);

    @Query("select sum(i.amount) from Payment i where i.type=1 and YEAR(i.logDate)=?1")
    Double getReimburseByYear(Integer year);

    @Query("select sum(i.amount) from Payment i where i.type=0 and i.currency=0 and YEAR(i.logDate)=?1 and MONTH(i.logDate)=?2")
    Double getPurchaseRMBByMonth(Integer year, Integer month);

    @Query("select sum(i.amount) from Payment i where i.type=0 and i.currency=1 and YEAR(i.logDate)=?1 and MONTH(i.logDate)=?2")
    Double getPurchaseUSDByMonth(Integer year, Integer month);

    @Query("select sum(i.amount) from Payment i where i.type=1 and YEAR(i.logDate)=?1 and MONTH(i.logDate)=?2")
    Double getReimburseByMonth(Integer year, Integer month);
}
