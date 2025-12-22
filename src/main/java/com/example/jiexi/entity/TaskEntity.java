package com.example.jiexi.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("task")   // 数据库表名
public class TaskEntity {

    @TableId(type = IdType.AUTO) // 主键 + 自增 + 回填
    private Long id;

    private Long userId;

    private String taskName;

    private Integer paperCount;

    private String status;      // WAITING / PARSING / DONE

    private LocalDateTime createTime;
}
