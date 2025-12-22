package com.example.jiexi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jiexi.entity.TaskEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskMapper extends BaseMapper<TaskEntity> {
}
