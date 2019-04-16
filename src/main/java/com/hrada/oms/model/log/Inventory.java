package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Product;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Created by shin on 2019-03-13.
 */
@Data
@Entity
@Table(name = "log_inventory")
public class Inventory extends BaseEntity {

    @OneToOne
    private Product product;
    private Integer amount=0;
    @ManyToMany
    private List<GDN> gdnList;
    @ManyToMany
    private List<GRN> grnList;
}
