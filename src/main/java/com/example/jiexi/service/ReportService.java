package com.example.jiexi.service;

import com.example.jiexi.dto.ReportDetailVO;
import com.example.jiexi.dto.ReportListVO;

import java.util.List;

public interface ReportService {
    List<ReportListVO> listReportsByTask(Long taskId);
    ReportDetailVO getReportDetail(Long reportId);
}
