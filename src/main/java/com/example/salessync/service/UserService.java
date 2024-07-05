package com.example.salessync.service;

import com.example.salessync.dto.user.UserRegistrationRequestDto;
import com.example.salessync.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto save(UserRegistrationRequestDto requestDto);
}
