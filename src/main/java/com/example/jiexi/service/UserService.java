package com.example.jiexi.service;

import com.example.jiexi.dto.UserLoginDTO;
import com.example.jiexi.dto.UserRegisterDTO;
import com.example.jiexi.dto.UserVO;
import com.example.jiexi.entity.UserEntity;

public interface UserService {

    // 注册用户
    UserVO register(UserRegisterDTO dto);

    // 登录验证
    UserVO login(UserLoginDTO dto);

    // 根据用户名查询用户
    UserEntity getByUsername(String username);
    UserVO getById(Long userId);
}
