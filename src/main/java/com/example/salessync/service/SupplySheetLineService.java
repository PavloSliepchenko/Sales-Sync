package com.example.salessync.service;

import com.example.salessync.dto.line.CreateSupplySheetLineRequestDto;
import com.example.salessync.dto.line.SupplySheetLineResponseDto;

public interface SupplySheetLineService {
    SupplySheetLineResponseDto addLine(Long userId,
                                       CreateSupplySheetLineRequestDto requestDto);

    SupplySheetLineResponseDto updateLine(
            Long userId, Long lineId, CreateSupplySheetLineRequestDto requestDto);

    void deleteLine(Long userId, Long lineId);
}
