package com.dev.thanhduy.task_backend.dto;

import com.dev.thanhduy.task_backend.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {

    private String token;

    private long user_id;

    private UserRole user_role;

}
