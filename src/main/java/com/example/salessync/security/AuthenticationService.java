package com.example.salessync.security;

import com.example.salessync.dto.user.UserLoginRequestDto;
import com.example.salessync.dto.user.UserLoginResponseDto;
import com.example.salessync.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserLoginResponseDto authenticate(UserLoginRequestDto requestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDto.email(),
                        requestDto.password())
        );
        String token = getToken(authentication.getName());
        User user = (User) authentication.getPrincipal();
        return getUserLoginResponseDto(user, token);
    }

    public UserLoginResponseDto getFreshToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String token = getToken(user.getEmail());
        return getUserLoginResponseDto(user, token);
    }

    public String getToken(String userName) {
        return jwtUtil.generateToken(userName);
    }

    private UserLoginResponseDto getUserLoginResponseDto(User user, String token) {
        return new UserLoginResponseDto(
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                token
        );
    }
}
