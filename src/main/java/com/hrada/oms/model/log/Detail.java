package com.hrada.oms.model.log;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Product;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by shin on 2018/7/30.
 */
@Data
@Entity
@Table(name = "log_stock_detail")
public class Detail extends BaseEntity {


    @OneToOne
    private Product product;
    private Integer amount;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private GRN grn;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private GDN gdn;

}
