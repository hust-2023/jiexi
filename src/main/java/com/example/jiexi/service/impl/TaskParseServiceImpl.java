package com.example.jiexi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jiexi.entity.PaperEntity;
import com.example.jiexi.entity.TaskEntity;
import com.example.jiexi.mapper.PaperMapper;
import com.example.jiexi.mapper.TaskMapper;
import com.example.jiexi.service.TaskParseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskParseServiceImpl implements TaskParseService {

    private final TaskMapper taskMapper;
    private final PaperMapper paperMapper;

    /**
     * 异步解析整个任务
     */
    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void parseTaskAsync(Long taskId) {

        log.info("【异步解析开始】taskId={}", taskId);

        // 1️⃣ 将任务状态改为 PARSING
        TaskEntity task = taskMapper.selectById(taskId);
        if (task == null) {
            log.error("任务不存在 taskId={}", taskId);
            return;
        }

        task.setStatus("PARSING");
        taskMapper.updateById(task);

        // 2️⃣ 查询该任务下所有 paper
        List<PaperEntity> papers = paperMapper.selectList(
                new QueryWrapper<PaperEntity>()
                        .eq("task_id", taskId)
        );

        // 3️⃣ 逐篇论文解析
        for (PaperEntity paper : papers) {

            try {
                log.info("开始解析论文 paperId={}, name={}",
                        paper.getId(), paper.getPaperName());

                // 3.1 论文状态 → PARSING
                paper.setParseStatus("PARSING");
                paperMapper.updateById(paper);

                // ===============================
                // ⭐ 模拟解析（后续换成 MinerU）
                Thread.sleep(3000);
                // ===============================

                // 3.2 解析成功
                paper.setParseStatus("DONE");
                paperMapper.updateById(paper);

                log.info("论文解析完成 paperId={}", paper.getId());

            } catch (Exception e) {

                log.error("论文解析失败 paperId={}", paper.getId(), e);

                // 失败兜底
                paper.setParseStatus("FAILED");
                paperMapper.updateById(paper);
            }
        }

        // 4️⃣ 所有论文解析完成 → 任务 DONE
        task.setStatus("DONE");
        taskMapper.updateById(task);

        log.info("【异步解析结束】taskId={}", taskId);
    }
}
