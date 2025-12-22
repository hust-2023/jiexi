package com.example.jiexi.controller;

import com.example.jiexi.dto.ReportUpdateDTO;
import com.example.jiexi.dto.ReportVO;
import com.example.jiexi.entity.ApiResponse;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ReportController {

    @GetMapping("/{taskId}")
    public ApiResponse<ReportVO> getReport(@PathVariable Long taskId) {
        return ApiResponse.success(null);
    }
    @PutMapping("/{taskId}")
    public ApiResponse<Void> updateReport(
            @PathVariable Long taskId,
            @RequestBody ReportUpdateDTO dto
    ) {
        return ApiResponse.success(null);
    }
    @GetMapping("/{taskId}/download")
    public void downloadReport(
            @PathVariable Long taskId,
            HttpServletResponse response
    ) {
        // TODO 设置 response header
    }


}
