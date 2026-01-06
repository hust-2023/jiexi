package com.example.jiexi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jiexi.dto.PaperVO;
import com.example.jiexi.dto.TaskDetailVO;
import com.example.jiexi.dto.TaskListVO;
import com.example.jiexi.entity.PaperEntity;
import com.example.jiexi.entity.TaskEntity;
import com.example.jiexi.mapper.PaperMapper;
import com.example.jiexi.mapper.TaskMapper;
import com.example.jiexi.service.TaskParseService;
import com.example.jiexi.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Slf4j
@Service
@Tag(name = "导读任务服务")
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final PaperMapper paperMapper;

    /** PDF 文件保存根目录 */
    private static final String UPLOAD_DIR = "D:/aaa_jiexi_temmp/";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTask(Long userId, String taskName, List<MultipartFile> files) {

        // 1️⃣ 参数校验
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("请至少上传一篇 PDF 论文");
        }

        // 2️⃣ 创建任务（数据库）
        TaskEntity task = new TaskEntity();
        task.setUserId(userId);
        task.setTaskName(taskName);
        task.setPaperCount(files.size());
        task.setStatus("WAITING");              // ✅ 初始状态为 WAITING
        task.setCreateTime(LocalDateTime.now());

        taskMapper.insert(task);
        Long taskId = task.getId();              // MP 自动回填主键

        // 3️⃣ 创建任务目录
        File taskDir = new File(UPLOAD_DIR + taskId);
        if (!taskDir.exists() && !taskDir.mkdirs()) {
            throw new RuntimeException("创建任务目录失败");
        }

        // 4️⃣ 保存 PDF + 写 paper 表
        for (MultipartFile file : files) {

            String originalName = file.getOriginalFilename();
            if (originalName == null || !originalName.toLowerCase().endsWith(".pdf")) {
                throw new IllegalArgumentException("仅支持 PDF 格式文件");
            }

            String fileName = UUID.randomUUID() + "_" + originalName;
            File destFile = new File(taskDir, fileName);

            try {
                file.transferTo(destFile);
            } catch (Exception e) {
                log.error("文件保存失败", e);
                throw new RuntimeException("文件保存失败");
            }

            PaperEntity paper = new PaperEntity();
            paper.setTaskId(taskId);
            paper.setPaperName(originalName);
            paper.setPdfPath(destFile.getAbsolutePath());
            paper.setParseStatus("WAITING"); // ✅ 初始状态为 WAITING
            paper.setCreateTime(LocalDateTime.now());

            paperMapper.insert(paper);
        }

        log.info("创建导读任务成功 taskId={}, paperCount={}", taskId, files.size());
        return taskId;
    }

    @Override
    public TaskDetailVO getTaskDetail(Long taskId) {

        TaskEntity task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }

        List<PaperEntity> paperEntities = paperMapper.selectList(
                new QueryWrapper<PaperEntity>().eq("task_id", taskId)
        );

        List<PaperVO> paperVOList = paperEntities.stream().map(paper -> {
            PaperVO vo = new PaperVO();
            vo.setPaperId(paper.getId());
            vo.setPaperName(paper.getPaperName());
            vo.setParseStatus(paper.getParseStatus());
            return vo;
        }).toList();

        TaskDetailVO vo = new TaskDetailVO();
        vo.setTaskId(task.getId());
        vo.setTaskName(task.getTaskName());
        vo.setStatus(task.getStatus());
        vo.setPaperCount(task.getPaperCount());
        vo.setCreateTime(task.getCreateTime());
        vo.setPapers(paperVOList);

        return vo;
    }

    @Override
    public List<TaskListVO> listTasks(Long userId) {

        List<TaskEntity> taskEntities = taskMapper.selectList(
                new QueryWrapper<TaskEntity>()
                        .eq("user_id", userId)
                        .orderByDesc("create_time")
        );

        return taskEntities.stream().map(task -> {
            TaskListVO vo = new TaskListVO();
            vo.setTaskId(task.getId());
            vo.setTaskName(task.getTaskName());
            vo.setStatus(task.getStatus());
            vo.setPaperCount(task.getPaperCount());
            vo.setCreateTime(task.getCreateTime().toString());
            return vo;
        }).toList();
    }
}
