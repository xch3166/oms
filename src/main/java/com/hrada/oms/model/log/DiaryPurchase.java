package com.hrada.oms.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Personal;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/10/19.
 */
@Data
@Entity
@Table(name="log_diaryPurchase")
public class DiaryPurchase extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date logDate;
    private String name;
    private String supplier;
    private Float total;
    @OneToOne
    private Personal applicant;
    @OneToOne
    private Personal approver;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="purchase_id")
    @JsonIgnore
    private List<DiaryPurchaseDetail> details;
    private Integer state;
}
