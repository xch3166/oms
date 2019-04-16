package com.hrada.oms.model.log;

import com.hrada.oms.model.model.Base;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/4/20.
 */
@Entity
@Table(name = "log_safety")
public class Safety {
    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private Base base;
    @Temporal(TemporalType.DATE)
    private Date sDate;
    @Temporal(TemporalType.DATE)
    private Date eDate;
    private Integer days;

    public Safety() {
    }

    public Safety(Base base, Date sDate, Date eDate, Integer days) {
        this.base = base;
        this.sDate = sDate;
        this.eDate = eDate;
        this.days = days;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public Date getsDate() {
        return sDate;
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    public Date geteDate() {
        return eDate;
    }

    public void seteDate(Date eDate) {
        this.eDate = eDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
