package com.example.jiexi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("task_report")  // 对应数据库表
public class TaskReportEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long taskId;       // 任务ID
    private Long userId;       // 用户ID
    private String type;       // TWEET | REPORT
    private String title;      // 报告标题
    private String contentMd;  // Markdown 内容
    private LocalDateTime createTime;
}
