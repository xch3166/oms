package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by shin on 2018/10/19.
 */
@Data
@Entity
@Table(name="log_diaryPurchase_detail")
public class DiaryPurchaseDetail extends BaseEntity {

    private String name;
    private String spec;
    private String unit;
    private Integer amount;
    private Double price;
    private Double total;
    @ManyToOne
    private DiaryPurchase purchase;
}
