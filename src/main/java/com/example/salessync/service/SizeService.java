package com.example.salessync.service;

import com.example.salessync.dto.sheet.SupplySheetResponseDto;
import com.example.salessync.dto.size.CreateSizeRequestDto;
import com.example.salessync.dto.size.SizeResponseDto;

public interface SizeService {
    SupplySheetResponseDto addSize(Long userId, Long sheetId, CreateSizeRequestDto requestDto);

    SizeResponseDto updateSize(Long userId, Long sheetId, Long lineId, Long sizeId, Integer number);

    void deleteSize(Long userId, Long sheetId, String sizeName);
}
