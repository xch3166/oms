package com.hrada.oms.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.common.Upload;
import com.hrada.oms.model.model.Client;
import com.hrada.oms.model.model.Personal;
import com.hrada.oms.model.model.Supplier;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/11/22.
 */
@Data
@ToString(exclude = "details")
@Entity
@Table(name = "log_contract")
public class Contract extends BaseEntity {

    private String no;
    @OneToOne
    private Supplier supplier;
    @OneToOne
    private Client client;
    @Temporal(TemporalType.DATE)
    private Date contractDate;
    @Temporal(TemporalType.DATE)
    private Date deliveryDate;
    private Double amount;
    //0:采购；1：销售；
    private Integer type;
    @OneToOne
    private Upload file;
    // 0：未审批；1：负责人审批通过；2：会计审批通过；3：负责人驳回；4：会计驳回；
    private Integer state=0;
    @Lob
    private String remark;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contract")
    private List<ContractDetail> details;
    @OneToOne
    private Personal applicant;
    @OneToOne
    private Personal approver;
    //质保金： 0：未付；1：已付；
    private Integer zhibaojin;
    @OneToOne
    private Contract sellContract;
}
