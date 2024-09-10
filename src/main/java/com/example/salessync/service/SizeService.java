package com.example.salessync.service;

import com.example.salessync.dto.sheet.SupplySheetResponseDto;
import com.example.salessync.dto.size.CreateSizeRequestDto;
import com.example.salessync.dto.size.SizeResponseDto;

public interface SizeService {
    SupplySheetResponseDto addSize(Long userId, CreateSizeRequestDto requestDto);

    SizeResponseDto updateSize(Long userId, Long lineId, Long sizeId, Integer number);

    void deleteSize(Long userId, String sizeName);
}
