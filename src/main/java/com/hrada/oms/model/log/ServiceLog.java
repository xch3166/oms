package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Base;
import com.hrada.oms.model.model.Equipment;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/4/17.
 */
@Data
@Entity
@Table(name = "log_service")
public class ServiceLog extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date logDate;
    @ManyToOne
    private Base base;
    @ManyToOne
    private Equipment equipment;
    private Double hours=0.0;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    private List<ServiceParts> serviceParts;


}
