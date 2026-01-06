package com.example.jiexi.service;

import com.example.jiexi.dto.ReportVO;
import com.example.jiexi.dto.ReportUpdateDTO;
import com.example.jiexi.entity.TaskReportEntity;

import java.util.List;

public interface ReportService {
    ReportVO getReportByTaskId(Long taskId);
    List<ReportVO> listReportsByTaskId(Long taskId);
    void updateReport(Long reportId, ReportUpdateDTO dto);
    TaskReportEntity getReportEntityByTaskId(Long taskId); // 下载文件时用
}
