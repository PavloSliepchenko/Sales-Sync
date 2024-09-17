package com.example.salessync.service;

import com.example.salessync.dto.cell.CellResponseDto;
import com.example.salessync.dto.cell.CreateCellRequestDto;
import com.example.salessync.dto.sheet.SupplySheetResponseDto;

public interface SizeService {
    SupplySheetResponseDto addSize(Long userId, CreateCellRequestDto requestDto);

    CellResponseDto updateSize(Long userId, Long lineId, Long sizeId, Integer number);

    void deleteSize(Long userId, String sizeName);
}
