package com.example.jiexi.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskStatusVO {

    private Long taskId;

    private TaskViewStatus viewStatus;

    private String taskStatus;    // task.status
    private String aiStatus;      // task.ai_status

    private int paperTotal;
    private int paperParsed;

    private List<PaperStatusVO> papers;
}
