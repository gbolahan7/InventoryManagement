package com.inventory.management.scheduler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class JobObject implements Serializable {
    private String groupKey;
    private String period;
    private Instant start;
    private Instant stop;
    private String updateType;
    private Double bonusPoint;
    private Instant triggerTime;
}