package com.example.jiexi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jiexi.entity.PaperEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaperMapper extends BaseMapper<PaperEntity> {

    List<PaperEntity> selectByTaskId(Long taskId);
}
