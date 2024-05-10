package com.contest.esg.controller;

import com.contest.esg.config.CommonResponse;
import com.contest.esg.controller.dto.LoginRequest;
import com.contest.esg.domain.User;
import com.contest.esg.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public CommonResponse<?> signup(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/all")
    public CommonResponse<List<User>> getAllUsers() {
        return CommonResponse.success(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public CommonResponse<User> getUserById(@Parameter(name = "id", description = "유저 고유 식별 번호(숫자)", in = ParameterIn.PATH) @PathVariable("id") Long userId) {
        return CommonResponse.success(userService.getUserById(userId));
    }

    @PostMapping("/login")
    public CommonResponse<?> login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
}