//package com.example.jiexi.service.impl;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class ReportServiceImpl implements ReportService {
//
//    private final TaskReportMapper reportMapper;
//
//    @Override
//    public List<ReportListVO> listReportsByTask(Long taskId) {
//        return reportMapper.selectByTaskId(taskId); // Mapper 返回 List<ReportListVO>
//    }
//
//    @Override
//    public ReportDetailVO getReportDetail(Long reportId) {
//        return reportMapper.selectDetailById(reportId); // Mapper 返回 ReportDetailVO
//    }
//}
