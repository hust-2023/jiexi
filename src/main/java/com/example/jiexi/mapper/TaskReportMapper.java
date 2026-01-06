package com.example.jiexi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jiexi.dto.ReportDetailVO;
import com.example.jiexi.dto.ReportListVO;
import com.example.jiexi.entity.TaskReportEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskReportMapper extends BaseMapper<TaskReportEntity> {

    @Select("SELECT id AS reportId, title, version, create_time AS createTime FROM task_report WHERE task_id = #{taskId} ORDER BY create_time ASC")
    List<ReportListVO> selectByTaskId(Long taskId);

    @Select("SELECT id AS reportId, title, content_md AS contentMd, content_html AS contentHtml, summary, version, create_time AS createTime FROM task_report WHERE id = #{reportId}")
    ReportDetailVO selectDetailById(Long reportId);
}
