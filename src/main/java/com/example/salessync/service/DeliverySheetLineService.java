package com.example.salessync.service;

import com.example.salessync.dto.line.CreateDeliverySheetLineRequestDto;
import com.example.salessync.dto.line.DeliverySheetLineResponseDto;
import com.example.salessync.dto.line.UpdateDeliverySheetLineRequestDto;

public interface DeliverySheetLineService {
    DeliverySheetLineResponseDto addLine(Long userId, CreateDeliverySheetLineRequestDto requestDto);

    DeliverySheetLineResponseDto updateLine(
            Long userId, Long lineId, UpdateDeliverySheetLineRequestDto requestDto);

    void deleteLine(Long userId, Long lineId);

    DeliverySheetLineResponseDto updateSupply(Long userId, Long lineId, Integer supply);
}
