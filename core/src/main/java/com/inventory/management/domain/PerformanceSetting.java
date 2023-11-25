package com.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Data
@Entity
@Audited
@Table(name = "performance_setting")
public class PerformanceSetting extends EntityLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private Instant staffStartTime;
    @Column
    private Instant staffStopTime;
    @Column
    private String staffPeriod;
    @Column
    private Double staffBonusPoint;
    @Column
    private String staffUpdateType;
    @Column(name = "version_id", nullable = false)
    @JsonIgnore
    private String version;
}
