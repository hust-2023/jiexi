package com.example.jiexi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jiexi.dto.ReportVO;
import com.example.jiexi.dto.ReportUpdateDTO;
import com.example.jiexi.entity.TaskReportEntity;
import com.example.jiexi.mapper.TaskReportMapper;
import com.example.jiexi.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final TaskReportMapper reportMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public ReportVO getReportByTaskId(Long taskId) {
        TaskReportEntity entity = reportMapper.selectOne(
                new QueryWrapper<TaskReportEntity>().eq("task_id", taskId)
        );
        return entity == null ? null : toVO(entity);
    }

    @Override
    public List<ReportVO> listReportsByTaskId(Long taskId) {
        List<TaskReportEntity> list = reportMapper.selectList(
                new QueryWrapper<TaskReportEntity>().eq("task_id", taskId)
        );
        return list.stream().map(this::toVO).collect(Collectors.toList());
    }

    public void updateReportByTaskId(Long taskId, ReportUpdateDTO dto) {
        TaskReportEntity entity = reportMapper.selectOne(
                new QueryWrapper<TaskReportEntity>().eq("task_id", taskId)
        );
        if (entity == null) return;
        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getContentMd() != null) entity.setContentMd(dto.getContentMd());
        reportMapper.updateById(entity);
    }
    @Override
    public void updateReport(Long reportId, ReportUpdateDTO dto) {
        TaskReportEntity entity = reportMapper.selectById(reportId);
        if (entity == null) return;

        if (dto.getTitle() != null) entity.setTitle(dto.getTitle());
        if (dto.getContentMd() != null) entity.setContentMd(dto.getContentMd());

        reportMapper.updateById(entity);
    }

    @Override
    public TaskReportEntity getReportEntityByTaskId(Long taskId) {
        return reportMapper.selectOne(
                new QueryWrapper<TaskReportEntity>().eq("task_id", taskId)
        );
    }

    /** 将 TaskReportEntity 转为前端使用的 VO */
    private ReportVO toVO(TaskReportEntity entity) {
        ReportVO vo = new ReportVO();
        vo.setId(entity.getId());
        vo.setTaskId(entity.getTaskId());
        vo.setTitle(entity.getTitle());
        vo.setType(entity.getType());
        vo.setContentMd(entity.getContentMd());
        if (entity.getCreateTime() != null) {
            vo.setCreateTime(entity.getCreateTime());
        }
        return vo;
    }
}
