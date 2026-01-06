package com.example.jiexi.assembler;

import com.example.jiexi.dto.PaperVO;
import com.example.jiexi.dto.TaskDetailVO;
import com.example.jiexi.dto.TaskListVO;
import com.example.jiexi.entity.PaperEntity;
import com.example.jiexi.entity.TaskEntity;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TaskVOAssembler
 * 将 TaskEntity + PaperEntity 列表转换为前端需要的 VO
 */
public class TaskVOAssembler {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Entity -> TaskListVO（任务列表页）
     */
    public static TaskListVO toTaskListVO(TaskEntity task, List<PaperEntity> papers) {
        TaskListVO vo = new TaskListVO();
        vo.setTaskId(task.getId());
        vo.setTaskName(task.getTaskName());
        vo.setPaperCount(task.getPaperCount());
        vo.setStatus(task.getStatus());
        vo.setCreateTime(task.getCreateTime().format(DATE_FORMATTER));

        List<PaperVO> paperVOList = papers.stream().map(paper -> {
            PaperVO pvo = new PaperVO();
            pvo.setPaperId(paper.getId());
            pvo.setPaperName(paper.getPaperName());
            pvo.setParseStatus(paper.getParseStatus());
            return pvo;
        }).collect(Collectors.toList());

        vo.setPapers(paperVOList);
        return vo;
    }

    /**
     * Entity -> TaskDetailVO（任务详情页）
     */
    public static TaskDetailVO toTaskDetailVO(TaskEntity task, List<PaperEntity> papers) {
        TaskDetailVO vo = new TaskDetailVO();
        vo.setTaskId(task.getId());
        vo.setTaskName(task.getTaskName());
        vo.setPaperCount(task.getPaperCount());
        vo.setStatus(task.getStatus());
        vo.setCreateTime(task.getCreateTime().format(DATE_FORMATTER));

        List<PaperVO> paperVOList = papers.stream().map(paper -> {
            PaperVO pvo = new PaperVO();
            pvo.setPaperId(paper.getId());
            pvo.setPaperName(paper.getPaperName());
            pvo.setParseStatus(paper.getParseStatus());
            return pvo;
        }).collect(Collectors.toList());

        vo.setPapers(paperVOList);
        return vo;
    }
}
