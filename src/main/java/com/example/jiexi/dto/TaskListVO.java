package com.example.jiexi.dto;

import lombok.Data;

@Data
public class TaskListVO {
    private Long taskId;
    private String taskName;
    private Integer paperCount;
    private String status;
    private String createTime;
}
