package com.contest.esg.service;

import com.contest.esg.config.CommonResponse;
import com.contest.esg.controller.dto.LoginRequest;
import com.contest.esg.domain.User;
import com.contest.esg.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public CommonResponse<?> createUser(User user) {
        // 빈칸이 존재하는지 확인
        if(user.getEmail().equals("") || user.getUserPwd().equals("") || user.getUserName().equals("") || user.getPhone().equals("") || user.getRegistrationNumber().equals("")){
            return CommonResponse.badRequest("빈칸 존재");
        }
        // email이 이미 존재하는지 확인
        if(userRepository.existsByEmail(user.getEmail())) {
            return CommonResponse.badRequest("이미 존재하는 아이디");
        }
        // userName이 이미 존재하는지 확인
        if(userRepository.existsByUserName(user.getUserName())) {
            return CommonResponse.badRequest("이미 존재하는 닉네임");
        }
        // 사용자의 비밀번호를 해싱하여 저장
        String hashedPassword = BCrypt.hashpw(user.getUserPwd(), BCrypt.gensalt());
        user.setUserPwd(hashedPassword);
        User savedUser = userRepository.save(user);
        return CommonResponse.success(savedUser);
    }

    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with userId: " + userId));
        return user;
    }

    public CommonResponse<?> login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("User not found with email: " + loginRequest.getEmail()));

        if (BCrypt.checkpw(loginRequest.getPwd(), user.getUserPwd())) {
            return CommonResponse.success(user);
        }
        return CommonResponse.unAuthorized();
    }
}
