package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Personal;
import com.hrada.oms.model.model.Supplier;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 入库单
 * Created by shin on 2018/7/27.
 */
@Data
@Entity
@Table(name = "log_grn")
public class GRN extends BaseEntity {

    @Temporal(TemporalType.DATE)
    private Date logDate;
    @OneToOne
    private Supplier supplier;
    private String no;
    @OneToOne
    private Contract contract;
    private Float total;
    private String warehouse;
    @OneToOne
    private Personal reviewer;
    @OneToOne
    private Personal founder;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Detail> details;
    @ManyToMany
    private List<Inventory> inventories;

}
