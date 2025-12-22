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

    // ✅ 由 Spring 注入 Service（非 static）
    private final TaskService taskService;

    @PostMapping("/create")
    @Operation(summary = "创建导读任务")
    public ApiResponse<Long> createTask(
            @RequestParam String taskName,
            @RequestParam List<MultipartFile> files
    ) {
        Long userId = 1L; // TODO 从 JWT 中获取
        Long taskId = taskService.createTask(userId, taskName, files);
        return ApiResponse.success(taskId);
    }

    @GetMapping
    @Operation(summary = "获取导读任务列表")
    public ApiResponse<List<TaskListVO>> listTasks() {
        // TODO 从 token 中取 userId
        return ApiResponse.success(null);
    }

    @GetMapping("/{taskId}")
    @Operation(summary = "获取导读任务详情")
    public ApiResponse<TaskDetailVO> taskDetail(@PathVariable Long taskId) {
        return ApiResponse.success(null);
    }
}
