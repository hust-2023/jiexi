package com.example.jiexi.service.impl;

import com.example.jiexi.entity.PaperEntity;
import com.example.jiexi.entity.TaskEntity;
import com.example.jiexi.mapper.PaperMapper;
import com.example.jiexi.mapper.TaskMapper;
import com.example.jiexi.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@Tag(name = "导读任务服务")
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final PaperMapper paperMapper;

    private static final String UPLOAD_DIR = "D:/aaa_jiexi_temmp/";

    @Override
    @Transactional
    public Long createTask(Long userId, String taskName, List<MultipartFile> files) {

        // 1️⃣ 参数校验
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("请至少上传一篇 PDF 论文");
        }

        // 2️⃣ 创建任务（写数据库）
        TaskEntity task = new TaskEntity();
        task.setUserId(userId);
        task.setTaskName(taskName);
        task.setPaperCount(files.size());
        task.setStatus("WAITING");
        task.setCreateTime(LocalDateTime.now());

        taskMapper.insert(task);           // ⭐ 数据库插入
        Long taskId = task.getId();        // ⭐ MyBatis-Plus 自动回填

        // 3️⃣ 创建任务目录
        File taskDir = new File(UPLOAD_DIR + taskId);
        if (!taskDir.exists() && !taskDir.mkdirs()) {
            throw new RuntimeException("创建任务目录失败");
        }

        // 4️⃣ 保存文件 + 插入 paper 表
        for (MultipartFile file : files) {

            if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
                throw new IllegalArgumentException("仅支持 PDF 格式文件");
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File destFile = new File(taskDir, fileName);

            try {
                file.transferTo(destFile);
            } catch (Exception e) {
                log.error("文件保存失败", e);
                throw new RuntimeException("文件保存失败");
            }

            PaperEntity paper = new PaperEntity();
            paper.setTaskId(taskId);
            paper.setPaperName(file.getOriginalFilename());
            paper.setPdfPath(destFile.getAbsolutePath());
            paper.setParseStatus("WAITING");
            paper.setCreateTime(LocalDateTime.now());

            paperMapper.insert(paper);     // ⭐ 数据库存储
        }

        log.info("创建导读任务成功 taskId={}, paperCount={}", taskId, files.size());
        return taskId;
    }
}
