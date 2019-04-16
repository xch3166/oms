package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.common.Enum;
import com.hrada.oms.model.common.Upload;
import com.hrada.oms.model.model.Personal;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/10/16.
 */
@Data
@Entity
@Table(name="log_payment")
public class Payment extends BaseEntity {

    private String logDate;
    private String receipt;
    private String account;
    private String bank;
    private String content;
    private Double amount;
    private String capital;
    private String remark;
    private Integer paper;
    @OneToOne
    private Personal applicant;
    @OneToOne
    private Personal approver;
    //1：会计通过；2：李总通过；3：出纳通过；4: 会计拒绝；5：李总拒绝；6：出纳拒绝；
    private Integer state=0;
    @Temporal(TemporalType.DATE)
    private Date processingTime;
    @OneToOne
    private Contract contract;
    //0: 采购；1：报销；
    private Integer type=0;
    //0: 人民币；1：美元；
    private Integer currency;
    @OneToOne
    private Upload file;
    @OneToOne
    private Enum company;
}
