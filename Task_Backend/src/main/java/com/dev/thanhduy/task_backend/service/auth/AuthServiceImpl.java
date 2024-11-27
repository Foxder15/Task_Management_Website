package com.dev.thanhduy.task_backend.service.auth;

import com.dev.thanhduy.task_backend.dto.SignupRequest;
import com.dev.thanhduy.task_backend.dto.UserDto;
import com.dev.thanhduy.task_backend.entity.User;
import com.dev.thanhduy.task_backend.enums.UserRole;
import com.dev.thanhduy.task_backend.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    @PostConstruct
    public void createAdminAccount() {
        Optional<User> admin = userRepository.findByUserRole(UserRole.ADMIN);

        if (admin.isEmpty()) {
            User user = new User();
            user.setEmail("admin@gmail.com");
            user.setName("ADMIN");
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            user.setUserRole(UserRole.ADMIN);
            userRepository.save(user);
            System.out.println("Admin account created successfully!");
        } else {
            System.out.println("Admin account already exists!");
        }
    }

    @Override
    public UserDto signupUser(SignupRequest signupRequest) {
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setName(signupRequest.getName());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.EMPLOYEE);
        User returnUser = userRepository.save(user);
        return returnUser.getUserDto();
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }
}
