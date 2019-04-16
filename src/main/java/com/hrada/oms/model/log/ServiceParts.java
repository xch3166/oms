package com.hrada.oms.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Equipment;
import com.hrada.oms.model.model.Module;
import com.hrada.oms.model.model.Parts;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by shin on 2018/4/17.
 */
@Data
@Entity
@Table(name = "log_service_parts")
public class ServiceParts extends BaseEntity {

    @OneToOne
    private Equipment equipment;
    @OneToOne
    private Module module;
    @OneToOne
    private Parts parts;
    private Integer amount;
    @ManyToOne
    @JsonIgnore
    private ServiceLog serviceLog;
}
