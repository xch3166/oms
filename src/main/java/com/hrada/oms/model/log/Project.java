package com.hrada.oms.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Client;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/12/7.
 */
@Data
@Entity
@Table(name = "log_project")
public class Project extends BaseEntity {

    private String name;
    private String country;
    @OneToOne
    private Client client;
    @Temporal(TemporalType.DATE)
    private Date sDate;
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    @Temporal(TemporalType.DATE)
    private Date eDate;
    //1：供应商开票完成；2：运费开票完成；3：报关完成；4：退税；
    private Integer state=0;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private List<ProjectDetail> details;
    private Integer kp=0;
    private Integer yf=0;
    private Integer bg=0;
    private Integer ts=0;
}
