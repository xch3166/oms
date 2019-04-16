package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Base;
import com.hrada.oms.model.model.Personal;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/4/16.
 */
@Data
@Entity
@Table(name = "log_injection")
public class InjectionLog extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date logDate;
    @ManyToOne
    private Base base;
    @ManyToOne
    private Personal personal;
    @Lob
    private String content;
    private Integer total=0;
    private Integer receive=0;
    private Integer solid=0;
    private Integer liquid=0;
    private Integer injection=0;
    private String category;
    private String amount;
    private Double hours=0.0;
    private Integer state;
    private Integer n1=0;
    private Integer n2=0;
    private Integer n3=0;
    private Integer n4=0;
    private Integer n5=0;
    private Integer n6=0;
    private Integer n7=0;
    private Integer n8=0;
    private Integer n9=0;
    private Integer n10=0;
    private Integer n11=0;
    private Integer n12=0;
    private Integer n13=0;
    private Integer n14=0;
    private Integer n15=0;
    private Integer n16=0;
    private Integer n17=0;
    private Integer n18=0;
    private Integer n19=0;
    private Integer n20=0;
    private Integer n21=0;
    private Integer n22=0;
    private Integer n23=0;
    private Integer n24=0;
    private Integer n25=0;
    private Integer n26=0;
    private Integer n27=0;
    private Integer n28=0;
    private Integer n29=0;
    private Integer n30=0;
    private Integer n31=0;
    private Integer n32=0;
    private Integer n33=0;
    private Integer n34=0;
    private Integer n35=0;
}
