package com.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "staff_performance")
public class StaffPerformance extends EntityLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false)
    private Long id;
    @Column(nullable = false)
    private Boolean isNew;
    @Column
    private String username;
    @Column
    private String period;
    @Column //inline or aggregate
    private String type;
    @Column(name = "app")
    private Double averagePeriodicPerformance;
    @Column(name = "bonus")
    private Double bonusPoint;
    @Column
    private String status;
    @Column(name = "version_id", nullable = false)
    @JsonIgnore
    private String version;
}
