package com.example.jiexi.dto;

public enum TaskViewStatus {
    WAITING,        // 等待解析
    PARSING,        // 有 paper 在解析
    AI_RUNNING,     // AI 在跑
    DONE,           // 全部完成
    FAILED
}

