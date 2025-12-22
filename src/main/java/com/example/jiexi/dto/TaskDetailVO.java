package com.example.jiexi.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskDetailVO {
    private Long taskId;
    private String taskName;
    private List<PaperVO> papers;
}

