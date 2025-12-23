package com.example.jiexi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("report")
public class ReportEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long taskId;

    private String content; // Markdown 内容

    private LocalDateTime createTime;
}
