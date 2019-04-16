package com.hrada.oms.model.log;

import com.hrada.oms.model.BaseEntity;
import com.hrada.oms.model.model.Supplier;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by shin on 2018/12/7.
 */
@Data
@Entity
@Table(name = "log_project_detail")
public class ProjectDetail extends BaseEntity {

    private String name;
    @OneToOne
    private Supplier supplier;
    @ManyToOne
    private Project project;
    private Integer state=0;
}
