package com.hrada.oms.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Personal;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/10/24.
 */
@Data
@Entity
@Table(name="log_travel")
public class Travel extends BaseEntity {

    private String name;
    private String no;
    private String address;
    private String department;
    @OneToOne
    private Personal applicant;
    @Temporal(TemporalType.DATE)
    private Date sDate;
    @Temporal(TemporalType.DATE)
    private Date eDate;
    private String content;
    private Double traffic;
    private Double lodging;
    private Double other;
    @OneToOne
    private Personal deptApprover;
    @Temporal(TemporalType.DATE)
    private Date deptDate;
    @OneToOne
    private Personal hrApprover;
    @Temporal(TemporalType.DATE)
    private Date hrDate;
    //0:未审核；1：部门领导审核通过；2：HR审核通过；3: 未通过
    private Integer state=0;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="travel_id")
    @JsonIgnore
    private List<TravelPeer> peers;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="travel_id")
    @JsonIgnore
    private List<TravelStroke> strokes;
}
