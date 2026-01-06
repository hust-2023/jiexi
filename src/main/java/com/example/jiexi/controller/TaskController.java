package com.example.jiexi.controller;

import com.example.jiexi.dto.TaskDetailVO;
import com.example.jiexi.dto.TaskListVO;
import com.example.jiexi.entity.ApiResponse;
import com.example.jiexi.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/create")
    @Operation(summary = "创建导读任务")
    public ApiResponse<Long> createTask(
            @RequestParam String taskName,
            @RequestParam List<MultipartFile> files
    ) {
        Long userId = 1L; // TODO: 后续替换为JWT获取
        Long taskId = taskService.createTask(userId, taskName, files);
        return ApiResponse.success(taskId);
    }

    @GetMapping("/list")
    @Operation(summary = "获取导读任务列表")
    public ApiResponse<List<TaskListVO>> listTasks(@RequestParam(required = false) Long userId) {
        if (userId == null) userId = 1L; // TODO: 后续替换为JWT获取
        List<TaskListVO> taskList = taskService.listTasks(userId);
        return ApiResponse.success(taskList);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "获取任务详情")
    public ApiResponse<TaskDetailVO> getTaskDetail(@PathVariable Long taskId) {
        TaskDetailVO detail = taskService.getTaskDetail(taskId);
        return ApiResponse.success(detail);
    }
}
