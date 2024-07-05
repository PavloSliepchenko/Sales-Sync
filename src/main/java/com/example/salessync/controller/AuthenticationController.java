package com.example.salessync.controller;

import com.example.salessync.dto.user.UserLoginRequestDto;
import com.example.salessync.dto.user.UserLoginResponseDto;
import com.example.salessync.dto.user.UserRegistrationRequestDto;
import com.example.salessync.dto.user.UserResponseDto;
import com.example.salessync.security.AuthenticationService;
import com.example.salessync.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
@Tag(name = "Authentication", description = "End points for registration and login")
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping(value = "/register")
    @Operation(summary = "Register", description = "Saves a new user to DB")
    public UserResponseDto registerUser(@RequestBody @Valid UserRegistrationRequestDto requestDto) {
        return userService.save(requestDto);
    }

    @PostMapping(value = "/login")
    @Operation(summary = "Login",
            description = "Checks if there is such user in DB and returns a token")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }
}
