package com.inventory.management.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "performance_setting_request")
public class PerformanceSettingRequest extends EntityLog implements RequestBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id", nullable = false, insertable = false)
    private long requestId;

    @Column(nullable = false)
    @Pattern(regexp = "^(create|modify|delete)$", message = "request.type.invalid.regex")
    @NotBlank(message = "field.not.blank")
    private String requestType;

    @Column(nullable = false)
    private String requestStatus;

    @Column(nullable = false)
    private String name;
    @Column
    private Instant staffStartTime;
    @Column
    private Instant staffStopTime;
    @Column
    private String staffPeriod;
    @Column
    private String staffUpdateType;
    @Column
    private Double staffBonusPoint;
    @Column
    private Long performanceSettingId;

    @Version
    @Column(name = "version_id", nullable = false)
    @JsonIgnore
    private int version;

}
