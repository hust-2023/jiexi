package com.example.jiexi.service;


import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TaskService {

    @Operation(
            summary = "创建导读任务并上传论文",
            description = "创建一个导读任务，支持同时上传多篇 PDF 论文"
    )
    Long createTask(Long userId, String taskName, List<MultipartFile> files);
}
