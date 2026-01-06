package com.example.jiexi.service;

import com.example.jiexi.dto.ReportVO;
import com.example.jiexi.dto.ReportUpdateDTO;
import com.example.jiexi.entity.TaskReportEntity;

import java.util.List;

public interface ReportService {

    /**
     * 根据任务ID获取报告
     */
    ReportVO getReportByTaskId(Long taskId);

    /**
     * 根据任务ID列出所有报告（可能一任务对应多条）
     */
    List<ReportVO> listReportsByTaskId(Long taskId);

    /**
     * 更新报告内容（通过任务ID更新，保持和 Controller 一致）
     */
    void updateReport(Long taskId, ReportUpdateDTO dto);

    /**
     * 获取 TaskReportEntity 用于下载
     */
    TaskReportEntity getReportEntityByTaskId(Long taskId);

    void updateReportByTaskId(Long taskId, ReportUpdateDTO dto);
}
