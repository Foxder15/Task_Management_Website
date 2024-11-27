package com.dev.thanhduy.task_backend.dto;

import com.dev.thanhduy.task_backend.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private long id;

    private String name;

    private String email;

    private String password;

    private UserRole userRole;

}
