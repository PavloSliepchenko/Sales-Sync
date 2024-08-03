package com.example.salessync.service;

import com.example.salessync.dto.line.CreateDeliverySheetLineRequestDto;
import com.example.salessync.dto.line.DeliverySheetLineResponseDto;

public interface DeliverySheetLineService {
    DeliverySheetLineResponseDto addLine(Long userId, CreateDeliverySheetLineRequestDto requestDto);

    DeliverySheetLineResponseDto updateLine(
            Long userId, Long lineId, CreateDeliverySheetLineRequestDto requestDto);

    void deleteLine(Long userId, Long lineId);
}
