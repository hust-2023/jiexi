package com.example.jiexi.controller;

import com.example.jiexi.dto.TaskListVO;
import com.example.jiexi.dto.UserVO;
import com.example.jiexi.entity.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/users")
    public ApiResponse<List<UserVO>> listUsers() {
        return ApiResponse.success(null);
    }

    @GetMapping("/tasks")
    public ApiResponse<List<TaskListVO>> listAllTasks() {
        return ApiResponse.success(null);
    }
}

