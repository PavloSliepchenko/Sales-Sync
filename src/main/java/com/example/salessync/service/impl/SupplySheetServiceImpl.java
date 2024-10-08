package com.example.salessync.service.impl;

import com.example.salessync.dto.sheet.SupplySheetResponseDto;
import com.example.salessync.exception.EntityNotFoundException;
import com.example.salessync.mapper.SupplySheetMapper;
import com.example.salessync.model.SupplySheet;
import com.example.salessync.model.User;
import com.example.salessync.repository.SupplySheetRepository;
import com.example.salessync.repository.UserRepository;
import com.example.salessync.service.SupplySheetService;
import java.util.ArrayList;
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
    public SupplySheetResponseDto getSheet(Long userId) {
        return sheetMapper.toDto(getSheetByUserId(userId));
    }

    @Override
    public void deleteSheetByUserId(Long userId) {
        sheetRepository.delete(getSheetByUserId(userId));
    }

    private SupplySheet getSheetByUserId(Long userId) {
        return sheetRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(
                                "User with ID %s doesn't have a supply sheet", userId)));
    }
}
