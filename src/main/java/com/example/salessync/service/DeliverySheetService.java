package com.example.salessync.service;

import com.example.salessync.dto.sheet.DeliverySheetResponseDto;

public interface DeliverySheetService {
    DeliverySheetResponseDto addSheet(Long userId);

    DeliverySheetResponseDto getSheet(Long userId);

    void deleteSheetByUserId(Long userId);
}
