package com.example.jiexi.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class TaskDetailVO {

    private Long taskId;
    private String taskName;
    private String status;
    private Integer paperCount;
    private LocalDateTime createTime;

    /** 论文列表，使用外部 PaperVO */
    private List<PaperVO> papers;
}
