package com.example.jiexi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("paper")   // 对应数据库表名
public class PaperEntity {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long taskId;

    private String paperName;

    private String pdfPath;

    private String parseStatus; // WAITING / DONE / FAILED

    private LocalDateTime createTime;
}
