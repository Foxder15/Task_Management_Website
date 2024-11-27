package com.dev.thanhduy.task_backend.service.auth;

import com.dev.thanhduy.task_backend.dto.SignupRequest;
import com.dev.thanhduy.task_backend.dto.UserDto;

public interface AuthService {

    UserDto signupUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}
