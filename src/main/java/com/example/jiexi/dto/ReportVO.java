package com.example.jiexi.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReportVO {
    private Long id;
    private Long taskId;
    private String type;
    private String title;
    private String contentMd;
    private LocalDateTime createTime;
}
