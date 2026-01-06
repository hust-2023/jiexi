package com.example.jiexi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.jiexi.entity.PaperEntity;
import com.example.jiexi.mapper.PaperMapper;
import com.example.jiexi.service.PaperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaperServiceImpl extends ServiceImpl<PaperMapper, PaperEntity> implements PaperService {

    @Override
    public List<PaperEntity> listByTask(Long taskId) {
        return list(new QueryWrapper<PaperEntity>().eq("task_id", taskId));
    }

    @Override
    public int countByTask(Long taskId) {
        return (int)count(new QueryWrapper<PaperEntity>().eq("task_id", taskId));
    }

    @Override
    public int countParsedByTask(Long taskId) {
        return (int)count(new QueryWrapper<PaperEntity>()
                .eq("task_id", taskId)
                .eq("parse_status", "PARSED"));
    }
}
