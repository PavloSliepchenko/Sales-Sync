package com.example.salessync.service.impl;

import com.example.salessync.dto.sheet.CreateSheetRequestDto;
import com.example.salessync.dto.sheet.SheetResponseDto;
import com.example.salessync.exception.EntityAlreadyExistsException;
import com.example.salessync.exception.EntityNotFoundException;
import com.example.salessync.mapper.SheetMapper;
import com.example.salessync.model.Sheet;
import com.example.salessync.model.User;
import com.example.salessync.repository.SheetRepository;
import com.example.salessync.repository.UserRepository;
import com.example.salessync.service.SheetService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SheetServiceImpl implements SheetService {
    private final SheetRepository sheetRepository;
    private final UserRepository userRepository;
    private final SheetMapper sheetMapper;

    @Override
    public SheetResponseDto addSheet(Long userId, CreateSheetRequestDto requestDto) {
        Optional<Sheet> sheetOptional = sheetRepository.findByName(requestDto.name());
        if (sheetOptional.isPresent()) {
            throw new EntityAlreadyExistsException(String.format(
                    "Sheet with name %s already exists", requestDto.name()));
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no user with ID " + userId));
        Sheet sheet = new Sheet();
        sheet.setName(requestDto.name());
        sheet.setUser(user);
        return sheetMapper.toDto(sheetRepository.save(sheet));
    }

    @Override
    public List<SheetResponseDto> getAllSheets(Long userId) {
        return sheetRepository.findAllByUserId(userId).stream()
                .map(sheetMapper::toDto)
                .toList();
    }

    @Override
    public SheetResponseDto getSheetById(Long userId, Long sheetId) {
        return sheetMapper.toDto(getSheetByIdAndUserId(sheetId, userId));
    }

    @Override
    public void deleteSheetById(Long userId, Long sheetId) {
        sheetRepository.delete(getSheetByIdAndUserId(sheetId, userId));
    }

    private Sheet getSheetByIdAndUserId(Long sheetId, Long userId) {
        return sheetRepository.findByIdAndUserId(sheetId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(
                                "User with ID %s doesn't have a sheet with ID %s",
                                userId, sheetId)));
    }
}
