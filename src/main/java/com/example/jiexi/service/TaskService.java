package com.example.jiexi.service;


import com.example.jiexi.dto.TaskDetailVO;
import com.example.jiexi.dto.TaskListVO;
import com.example.jiexi.entity.TaskEntity;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {

    // 创建任务
    Long createTask(Long userId, String taskName, List<MultipartFile> files);

    // 查询用户所有任务（返回实体）
    List<TaskListVO> listTasks(Long userId);
    TaskDetailVO getTaskDetailByUser(Long taskId, Long userId);

    // 根据ID获取任务
    TaskDetailVO getTaskDetail(Long taskId);
}
