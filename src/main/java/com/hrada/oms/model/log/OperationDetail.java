package com.hrada.oms.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.model.Equipment;
import com.hrada.oms.model.model.Module;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/8/7.
 */
@Data
@Entity
@Table(name = "log_operation_detail")
public class OperationDetail {

    @Id
    @GeneratedValue
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date logDate;
    @OneToOne
    private Equipment equipment;
    @OneToOne
    private Module module;
    private Double hour;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Operation operation;
}
