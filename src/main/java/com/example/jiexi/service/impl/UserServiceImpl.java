package com.example.jiexi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.jiexi.dto.UserLoginDTO;
import com.example.jiexi.dto.UserRegisterDTO;
import com.example.jiexi.dto.UserVO;
import com.example.jiexi.entity.UserEntity;
import com.example.jiexi.mapper.UserMapper;
import com.example.jiexi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserVO register(UserRegisterDTO dto) {
        // 1. 检查用户名是否存在
        if (getByUsername(dto.getUsername()) != null) {
            return null; // 可以在Controller里返回提示 "用户名已存在"
        }

        // 2. 创建实体
        UserEntity entity = new UserEntity();
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword()); // 明文密码
        entity.setRole("USER");

        // 3. 保存到数据库
        userMapper.insert(entity);

        // 4. 转换成VO返回
        UserVO vo = new UserVO();
        vo.setId(entity.getId());
        vo.setUsername(entity.getUsername());
        vo.setRole(entity.getRole());
        return vo;
    }

    @Override
    public UserVO login(UserLoginDTO dto) {
        UserEntity entity = getByUsername(dto.getUsername());
        if (entity == null) return null;

        // 明文比对
        if (!entity.getPassword().equals(dto.getPassword())) {
            return null;
        }

        UserVO vo = new UserVO();
        vo.setId(entity.getId());
        vo.setUsername(entity.getUsername());
        vo.setRole(entity.getRole());
        return vo;
    }
    @Override
    public UserVO getById(Long userId) {
        UserEntity entity = userMapper.selectById(userId);
        if (entity == null) return null;

        UserVO vo = new UserVO();
        vo.setId(entity.getId());
        vo.setUsername(entity.getUsername());
        return vo;
    }

    @Override
    public UserEntity getByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<UserEntity>().eq("username", username));
    }
}
