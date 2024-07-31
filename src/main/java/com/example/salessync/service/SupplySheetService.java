package com.example.salessync.service;

import com.example.salessync.dto.sheet.CreateSupplySheetRequestDto;
import com.example.salessync.dto.sheet.SupplySheetResponseDto;
import java.util.List;

public interface SupplySheetService {
    SupplySheetResponseDto addSheet(Long userId, CreateSupplySheetRequestDto requestDto);

    List<SupplySheetResponseDto> getAllSheets(Long userId);

    SupplySheetResponseDto getSheetById(Long userId, Long sheetId);

    SupplySheetResponseDto updateSheetName(Long userId, Long sheetId,
                                           CreateSupplySheetRequestDto requestDto);

    void deleteSheetById(Long userId, Long sheetId);
}
