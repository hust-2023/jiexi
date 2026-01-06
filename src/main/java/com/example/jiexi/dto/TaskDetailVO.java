package com.example.jiexi.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskDetailVO {
    private Long taskId;
    private String taskName;
    private Integer paperCount;
    private String status;
    private String createTime;
    private List<PaperVO> papers;  // 论文列表及状态
}
