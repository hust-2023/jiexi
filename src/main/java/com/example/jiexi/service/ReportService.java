package com.example.jiexi.service;

import com.example.jiexi.dto.ReportVO;
import com.example.jiexi.dto.ReportUpdateDTO;
import com.example.jiexi.entity.TaskReportEntity;

import java.util.List;

public interface ReportService {

    // ✅ 新增：根据任务ID + 用户ID获取报告（用户隔离）
    ReportVO getReportByTaskIdAndUser(Long taskId, Long userId);

    void updateReportByTaskIdAndUser(Long taskId, Long userId, ReportUpdateDTO dto);

    TaskReportEntity getReportEntityByTaskIdAndUser(Long taskId, Long userId);

    /** 旧版方法，兼容 Controller 使用 */
    ReportVO getReportByTaskId(Long taskId);

    List<ReportVO> listReportsByTaskId(Long taskId);

    void updateReportByTaskId(Long taskId, ReportUpdateDTO dto);

    void updateReport(Long reportId, ReportUpdateDTO dto);

    TaskReportEntity getReportEntityByTaskId(Long taskId);
}
