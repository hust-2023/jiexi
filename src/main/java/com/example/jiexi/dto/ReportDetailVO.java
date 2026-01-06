package com.example.jiexi.dto;

import lombok.Data;

@Data
public class ReportDetailVO {
    private Long reportId;
    private String title;
    private String contentMd;
    private String contentHtml;
    private String summary;
    private Integer version;
    private String createTime;
}