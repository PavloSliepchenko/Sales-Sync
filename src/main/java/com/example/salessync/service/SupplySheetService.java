package com.example.salessync.service;

import com.example.salessync.dto.sheet.SupplySheetResponseDto;
import java.util.List;

public interface SupplySheetService {
    SupplySheetResponseDto addSheet(Long userId);

    List<SupplySheetResponseDto> getAllSheets(Long userId);

    SupplySheetResponseDto getSheetById(Long userId, Long sheetId);

    void deleteSheetById(Long userId, Long sheetId);
}
