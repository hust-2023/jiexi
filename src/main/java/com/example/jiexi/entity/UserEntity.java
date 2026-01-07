package com.example.jiexi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("user")
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private String role;
    private LocalDateTime createTime;
}
