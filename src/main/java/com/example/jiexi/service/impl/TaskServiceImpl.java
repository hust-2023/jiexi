package com.example.jiexi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jiexi.dto.PaperVO;
import com.example.jiexi.dto.TaskDetailVO;
import com.example.jiexi.dto.TaskListVO;
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
import java.time.format.DateTimeFormatter;
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

    private static final String UPLOAD_DIR = "D:/aaa_jiexi_temmp/";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createTask(Long userId, String taskName, List<MultipartFile> files) {
        if (files == null || files.isEmpty()) {
            throw new IllegalArgumentException("请至少上传一篇 PDF 论文");
        }

        // 创建任务
        TaskEntity task = new TaskEntity();
        task.setUserId(userId);
        task.setTaskName(taskName);
        task.setPaperCount(files.size());
        task.setStatus("WAITING");
        task.setCreateTime(LocalDateTime.now());

        taskMapper.insert(task);
        Long taskId = task.getId();

        // 创建任务目录
        File taskDir = new File(UPLOAD_DIR + taskId);
        if (!taskDir.exists() && !taskDir.mkdirs()) {
            throw new RuntimeException("创建任务目录失败");
        }

        // 保存文件 + 写入 Paper 表
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
            paper.setParseStatus("WAITING");
            paper.setCreateTime(LocalDateTime.now());

            paperMapper.insert(paper);
        }

        log.info("创建导读任务成功 taskId={}, paperCount={}", taskId, files.size());
        return taskId;
    }

    @Override
    public TaskDetailVO getTaskDetail(Long taskId) {
        // 原始方法，不限制用户（内部/管理员用）
        TaskEntity task = taskMapper.selectById(taskId);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }

        List<PaperEntity> papers = paperMapper.selectList(
                new QueryWrapper<PaperEntity>().eq("task_id", taskId)
        );

        List<PaperVO> paperVOList = papers.stream().map(paper -> {
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
        vo.setCreateTime(task.getCreateTime().format(DATE_FORMATTER));
        vo.setPapers(paperVOList);

        return vo;
    }

    @Override
    public TaskDetailVO getTaskDetailByUser(Long taskId, Long userId) {
        // ✅ 用户隔离：仅查询该用户的任务
        TaskEntity task = taskMapper.selectOne(
                new QueryWrapper<TaskEntity>()
                        .eq("id", taskId)
                        .eq("user_id", userId)
        );

        if (task == null) {
            throw new RuntimeException("任务不存在或无权限查看");
        }

        List<PaperEntity> papers = paperMapper.selectList(
                new QueryWrapper<PaperEntity>().eq("task_id", taskId)
        );

        List<PaperVO> paperVOList = papers.stream().map(paper -> {
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
        vo.setCreateTime(task.getCreateTime().format(DATE_FORMATTER));
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
            List<PaperEntity> papers = paperMapper.selectList(
                    new QueryWrapper<PaperEntity>().eq("task_id", task.getId())
                            .orderByAsc("id")
            );

            List<PaperVO> paperVOList = papers.stream().map(paper -> {
                PaperVO vo = new PaperVO();
                vo.setPaperId(paper.getId());
                vo.setPaperName(paper.getPaperName());
                vo.setParseStatus(paper.getParseStatus());
                return vo;
            }).toList();

            TaskListVO vo = new TaskListVO();
            vo.setTaskId(task.getId());
            vo.setTaskName(task.getTaskName());
            vo.setStatus(task.getStatus());
            vo.setPaperCount(task.getPaperCount());
            vo.setCreateTime(task.getCreateTime().format(DATE_FORMATTER));
            vo.setPapers(paperVOList);
            return vo;
        }).toList();
    }
}
