package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Product;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by shin on 2019-03-14.
 */
@Data
@Entity
@Table(name = "log_contract_detail")
public class ContractDetail extends BaseEntity {

    @OneToOne
    private Product product;
    @ManyToOne
    @JoinColumn(name = "contract_id", referencedColumnName = "id")
    private Contract contract;
    private Integer amount;
    private Double price;
}
