package com.hrada.oms.model.log;

import com.hrada.oms.model.model.Base;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/8/7.
 */
@Entity
@Table(name = "log_operation")
public class Operation {

    @Id
    @GeneratedValue
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date logDate;
    @OneToOne
    private Base base;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "operation_id")
    private List<OperationDetail> details;

    public Operation() {
    }

    public Operation(Date logDate, Base base, List<OperationDetail> details) {
        this.logDate = logDate;
        this.base = base;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public Base getBase() {
        return base;
    }

    public void setBase(Base base) {
        this.base = base;
    }

    public List<OperationDetail> getDetails() {
        return details;
    }

    public void setDetails(List<OperationDetail> details) {
        this.details = details;
    }
}
