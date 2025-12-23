package com.example.jiexi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jiexi.entity.PaperEntity;
import com.example.jiexi.entity.ReportEntity;
import com.example.jiexi.entity.TaskEntity;
import com.example.jiexi.mapper.PaperMapper;
import com.example.jiexi.mapper.ReportMapper;
import com.example.jiexi.mapper.TaskMapper;
import com.example.jiexi.service.TaskParseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskParseServiceImpl implements TaskParseService {

    private final TaskMapper taskMapper;
    private final PaperMapper paperMapper;
    private final ReportMapper reportMapper;

    /**
     * 异步解析任务
     */
    @Async
    @Override
    public void parseTaskAsync(Long taskId) {

        log.info("开始解析任务 taskId={}", taskId);

        // 1️⃣ 更新任务状态为 PARSING
        TaskEntity task = taskMapper.selectById(taskId);
        task.setStatus("PARSING");
        taskMapper.updateById(task);

        // 2️⃣ 查询任务下所有论文
        List<PaperEntity> papers = paperMapper.selectList(
                new QueryWrapper<PaperEntity>()
                        .eq("task_id", taskId)
        );

        StringBuilder markdownBuilder = new StringBuilder();
        markdownBuilder.append("# 导读报告\n\n");

        // 3️⃣ 逐篇解析
        for (PaperEntity paper : papers) {
            try {
                log.info("解析论文：{}", paper.getPaperName());

                // TODO：调用 MinerU（下一步我会替你接）
                String md = mockMinerUParse(paper.getPdfPath());

                markdownBuilder.append("## ")
                        .append(paper.getPaperName())
                        .append("\n\n")
                        .append(md)
                        .append("\n\n");

                paper.setParseStatus("DONE");
            } catch (Exception e) {
                log.error("论文解析失败：{}", paper.getPaperName(), e);
                paper.setParseStatus("FAILED");
            }

            paperMapper.updateById(paper);
        }

        // 4️⃣ 保存报告
        ReportEntity report = new ReportEntity();
        report.setTaskId(taskId);
        report.setContent(markdownBuilder.toString());
        report.setCreateTime(LocalDateTime.now());
        reportMapper.insert(report);

        // 5️⃣ 更新任务状态
        task.setStatus("DONE");
        taskMapper.updateById(task);

        log.info("任务解析完成 taskId={}", taskId);
    }

    /**
     * 模拟 MinerU 解析（占位）
     */
    private String mockMinerUParse(String pdfPath) {
        return "- 摘要：这是解析后的摘要内容\n"
                + "- 方法：这是方法部分\n"
                + "- 结论：这是结论部分";
    }
}
