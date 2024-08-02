package com.example.salessync.service.impl;

import com.example.salessync.dto.sheet.SupplySheetResponseDto;
import com.example.salessync.exception.EntityAlreadyExistsException;
import com.example.salessync.exception.EntityNotFoundException;
import com.example.salessync.mapper.SupplySheetMapper;
import com.example.salessync.model.SupplySheet;
import com.example.salessync.model.User;
import com.example.salessync.repository.SupplySheetRepository;
import com.example.salessync.repository.UserRepository;
import com.example.salessync.service.SupplySheetService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplySheetServiceImpl implements SupplySheetService {
    private static final String SUPPLY = "Supply";
    private final SupplySheetRepository sheetRepository;
    private final UserRepository userRepository;
    private final SupplySheetMapper sheetMapper;

    @Override
    public SupplySheetResponseDto addSheet(Long userId) {
        Optional<SupplySheet> sheetOptional = sheetRepository.findByUserId(userId);
        if (sheetOptional.isPresent()) {
            throw new EntityAlreadyExistsException(String.format(
                    "Sheet with name %s already exists", SUPPLY));
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no user with ID " + userId));
        SupplySheet sheet = new SupplySheet();
        sheet.setName(SUPPLY);
        sheet.setUser(user);
        sheet.setLines(new ArrayList<>());
        return sheetMapper.toDto(sheetRepository.save(sheet));
    }

    @Override
    public List<SupplySheetResponseDto> getAllSheets(Long userId) {
        return sheetRepository.findAllByUserId(userId).stream()
                .map(sheetMapper::toDto)
                .toList();
    }

    @Override
    public SupplySheetResponseDto getSheetById(Long userId, Long sheetId) {
        return sheetMapper.toDto(getSheetByIdAndUserId(sheetId, userId));
    }

    @Override
    public void deleteSheetById(Long userId, Long sheetId) {
        sheetRepository.delete(getSheetByIdAndUserId(sheetId, userId));
    }

    private SupplySheet getSheetByIdAndUserId(Long sheetId, Long userId) {
        return sheetRepository.findByIdAndUserId(sheetId, userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(
                                "User with ID %s doesn't have a sheet with ID %s",
                                userId, sheetId)));
    }
}
