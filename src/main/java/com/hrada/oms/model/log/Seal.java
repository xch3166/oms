package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Personal;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by shin on 2018/10/18.
 * 用章申请
 */
@Data
@Entity
@Table(name = "log_seal")
public class Seal extends BaseEntity {

    private String logDate;
    private String content;
    //0:未审核；1：审核通过；2：审核未通过；
    private Integer state=0;
    @OneToOne
    private Personal applicant;
    @OneToOne
    private Personal approver;
    @Temporal(TemporalType.DATE)
    private Date processingTime;
    //0：公章；1：合同章;2：法人人名章；3：财务专用章；4：发票专用章；
    private Integer name=0;
    //0:用印；1：外用
    private Integer type=0;
    private Integer count=1;
}
