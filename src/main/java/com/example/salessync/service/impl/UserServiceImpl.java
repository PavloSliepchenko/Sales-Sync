package com.example.salessync.service.impl;

import com.example.salessync.dto.user.UserRegistrationRequestDto;
import com.example.salessync.dto.user.UserResponseDto;
import com.example.salessync.exception.RegistrationException;
import com.example.salessync.mapper.UserMapper;
import com.example.salessync.model.User;
import com.example.salessync.repository.UserRepository;
import com.example.salessync.service.DeliverySheetService;
import com.example.salessync.service.SupplySheetService;
import com.example.salessync.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final User.Role DEFAULT_ROLE = User.Role.USER;
    private final DeliverySheetService deliverySheetService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final SupplySheetService supplySheetService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto save(UserRegistrationRequestDto requestDto) {
        if (findUserByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException(String.format("The user with email %s already exists",
                    requestDto.getEmail()));
        }
        User user = userMapper.toModel(requestDto);
        user.setRole(DEFAULT_ROLE);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user = userRepository.save(user);
        supplySheetService.addSheet(user.getId());
        deliverySheetService.addSheet(user.getId());
        return userMapper.toDto(user);
    }

    private Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
