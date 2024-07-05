package com.example.salessync.dto.user;

public record UserLoginResponseDto(String firstName,
                                   String lastName,
                                   String email,
                                   String token) {
}
