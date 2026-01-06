package com.example.jiexi.service;

import com.example.jiexi.entity.PaperEntity;

import java.util.List;

public interface PaperService {

    // 根据任务ID获取所有论文
    List<PaperEntity> listByTask(Long taskId);

    // 统计任务论文总数（可选）
    int countByTask(Long taskId);

    // 统计已解析论文数（可选）
    int countParsedByTask(Long taskId);
}
