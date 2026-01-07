package com.example.jiexi.controller;

import com.example.jiexi.dto.UserLoginDTO;
import com.example.jiexi.dto.UserRegisterDTO;
import com.example.jiexi.dto.UserVO;
import com.example.jiexi.entity.ApiResponse;
import com.example.jiexi.security.JwtUtil;
import com.example.jiexi.security.UserContext;
import com.example.jiexi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    // ================== 注册 ==================
    @PostMapping("/register")
    public ApiResponse<UserVO> register(@RequestBody UserRegisterDTO dto) {
        UserVO vo = userService.register(dto);
        if (vo == null) {
            return ApiResponse.error("用户名已存在");
        }
        return ApiResponse.success(vo);
    }

    // ================== 登录 ==================
    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody UserLoginDTO dto) {
        UserVO vo = userService.login(dto);
        if (vo == null) {
            return ApiResponse.error("用户名或密码错误");
        }

        // ✅ 生成 JWT（包含 userId + username）
        String token = jwtUtil.generateToken(vo.getId(), vo.getUsername());
        return ApiResponse.success(token);
    }

    // ================== 获取当前登录用户信息 ==================
    @GetMapping("/profile")
    public ApiResponse<UserVO> profile() {

        // ✅ 从 UserContext 获取当前登录用户ID（由全局拦截器填充）
        Long userId = UserContext.getUserId();

        // ✅ 查询用户信息
        UserVO vo = userService.getById(userId);
        if (vo == null) {
            return ApiResponse.error("用户不存在");
        }

        return ApiResponse.success(vo);
    }
}
