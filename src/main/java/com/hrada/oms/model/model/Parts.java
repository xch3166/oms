package com.hrada.oms.model.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * Created by shin on 2018/4/8.
 */
@Data
@ToString(exclude = "moduleList")
@Entity
@Table(name="model_parts")
public class Parts extends BaseEntity {

    private String name;
    private String type;
    private String spec;
    private String no;
    private Integer amount;
    private Integer state;
    private String types;
    private String equipments;
    private String modules;
    private Double hours=0.0;

    @OneToMany
    private List<PartsType> partsTypeList;

    @ManyToMany
    @JoinTable(
        name="model_equipment_parts",
        joinColumns=@JoinColumn(name="parts_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="equipment_id", referencedColumnName="id")
    )
    @JsonIgnore
    private List<Equipment> equipmentList;

    @ManyToMany
    @JoinTable(
        name="model_module_parts",
        joinColumns=@JoinColumn(name="parts_id", referencedColumnName="id"),
        inverseJoinColumns=@JoinColumn(name="module_id", referencedColumnName="id")
    )
    @JsonIgnore
    private List<Module> moduleList;

}
