package com.hrada.oms.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.model.Supplier;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by shin on 2018/9/12.
 */
@Entity
@Table( name="log_purchase")
public class Purchase {

    @Id
    @GeneratedValue
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date logDate;
    private String name;
    private Double total;
    private String contract;
    @OneToOne
    private Supplier supplier;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="purchase_id")
    @JsonIgnore
    private List<PurchaseDetail> details;

    public Purchase() {
    }

    public Purchase(Date logDate, String name, Supplier supplier, Double total, String contract, List<PurchaseDetail> details) {
        this.logDate = logDate;
        this.name = name;
        this.supplier = supplier;
        this.total = total;
        this.contract = contract;
        this.details = details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public List<PurchaseDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PurchaseDetail> details) {
        this.details = details;
    }
}
