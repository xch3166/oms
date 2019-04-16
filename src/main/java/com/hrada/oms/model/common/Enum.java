package com.hrada.oms.model.common;

import com.hrada.oms.model.BaseEntity;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by shin on 2018/8/9.
 */
@Data
@Entity
@Table(name = "common_enum")
public class Enum extends BaseEntity {

    private String name;
    private Integer state;
    @OneToOne
    private Enum parent;

}
