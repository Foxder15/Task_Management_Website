package com.dev.thanhduy.task_backend.controller.auth;

import com.dev.thanhduy.task_backend.dto.AuthenticationRequest;
import com.dev.thanhduy.task_backend.dto.AuthenticationResponse;
import com.dev.thanhduy.task_backend.dto.SignupRequest;
import com.dev.thanhduy.task_backend.dto.UserDto;
import com.dev.thanhduy.task_backend.entity.User;
import com.dev.thanhduy.task_backend.repository.UserRepository;
import com.dev.thanhduy.task_backend.service.auth.AuthService;
import com.dev.thanhduy.task_backend.service.jwt.UserService;
import com.dev.thanhduy.task_backend.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;

    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if (authService.hasUserWithEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email already in use");
        }
        UserDto createdUser = authService.signupUser(signupRequest);
        if (createdUser == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not created");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getEmail(),
                    authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
             throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(authenticationRequest.getEmail());
        System.out.println(optionalUser.get());
        final String token = jwtUtil.generateJwtToken(userDetails);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();

        if (optionalUser.isPresent()) {
            authenticationResponse.setToken(token);
            authenticationResponse.setUser_id(optionalUser.get().getId());
            authenticationResponse.setUser_role(optionalUser.get().getUserRole());
        }

        return authenticationResponse;
     }
}
