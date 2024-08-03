package com.example.salessync.service;

import com.example.salessync.dto.sheet.SupplySheetResponseDto;

public interface SupplySheetService {
    SupplySheetResponseDto addSheet(Long userId);

    SupplySheetResponseDto getSheet(Long userId);

    void deleteSheetByUserId(Long userId);
}
