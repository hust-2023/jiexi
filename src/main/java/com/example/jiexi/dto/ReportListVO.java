package com.example.jiexi.dto;

import lombok.Data;

@Data
public class ReportListVO {
    private Long reportId;
    private String title;
    private Integer version;
    private String createTime;
}