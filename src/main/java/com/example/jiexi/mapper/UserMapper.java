package com.example.jiexi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.jiexi.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {
}
