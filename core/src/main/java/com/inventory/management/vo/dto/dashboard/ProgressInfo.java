package com.inventory.management.vo.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProgressInfo {
    private String title;
    private Object value;
    private int activeProgress;
    private String description;
}
