package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Client;
import com.hrada.oms.model.model.Personal;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 出库单
 * Created by shin on 2018/7/27.
 */
@Data
@Entity
@Table(name = "log_gdn")
public class GDN extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date logDate;
    @OneToOne
    private Client client;
    private String no;
    private String address;
    private Float total;
    private String warehouse;
    private String signer;
    @OneToOne
    private Contract contract;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Detail> details;
    @OneToOne
    private Personal applicant;
    @OneToOne
    private Personal approver;
    // 0：未审批；1：负责人审批通过；2：会计审批通过；3：负责人驳回；4：会计驳回；
    private Integer state=0;
    @ManyToMany
    private List<Inventory> inventories;
}
