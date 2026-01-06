package com.example.jiexi.controller;

import com.example.jiexi.dto.ReportUpdateDTO;
import com.example.jiexi.dto.ReportVO;
import com.example.jiexi.entity.ApiResponse;
import com.example.jiexi.entity.TaskReportEntity;
import com.example.jiexi.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{taskId}")
    public ApiResponse<ReportVO> getReport(@PathVariable Long taskId) {
        ReportVO vo = reportService.getReportByTaskId(taskId);
        if (vo == null) return ApiResponse.error("报告不存在");
        return ApiResponse.success(vo);
    }

    @PutMapping("/task/{taskId}")
    public ApiResponse<Void> updateReportByTaskId(
            @PathVariable Long taskId,
            @RequestBody ReportUpdateDTO dto
    ) {
        reportService.updateReportByTaskId(taskId, dto);
        return ApiResponse.success(null);
    }

    @GetMapping("/{taskId}/download")
    public void downloadReport(
            @PathVariable Long taskId,
            HttpServletResponse response
    ) throws IOException {

        TaskReportEntity entity = reportService.getReportEntityByTaskId(taskId);
        if (entity == null) {
            response.sendError(404, "报告不存在");
            return;
        }

        // 1️⃣ 设置内容类型和编码
        response.setContentType("text/markdown;charset=UTF-8");

        // 2️⃣ 处理文件名中文
        String fileName = "report_" + taskId + ".md";
        fileName = java.net.URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        // 3️⃣ 写入文件内容
        response.getWriter().write(entity.getContentMd());
        response.getWriter().flush();
    }

}
