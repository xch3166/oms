package com.hrada.oms.model.log;

import javax.persistence.*;

/**
 * Created by shin on 2018/9/20.
 */
@Entity
@Table(name="log_purchase_detail")
public class PurchaseDetail {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String spec;
    private String unit;
    private Integer amount;
    private Double price;
    private Double total;
    @ManyToOne
    private Purchase purchase;

    public PurchaseDetail() {
    }

    public PurchaseDetail(String name, String spec, String unit, Integer amount, Double price, Double total, Purchase purchase) {
        this.name = name;
        this.spec = spec;
        this.unit = unit;
        this.amount = amount;
        this.price = price;
        this.total = total;
        this.purchase = purchase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }
}
