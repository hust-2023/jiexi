package com.example.jiexi.dto;

import lombok.Data;

import java.util.List;

@Data
public class TaskListVO {
    private Long taskId;
    private String taskName;
    private Integer paperCount;
    private String status;          // WAITING / PARSING / DONE
    private String createTime;      // 格式化后的时间
    private List<PaperVO> papers;   // 每篇论文状态
}
