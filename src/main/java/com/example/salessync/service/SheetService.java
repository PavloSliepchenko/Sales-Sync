package com.example.salessync.service;

import com.example.salessync.dto.sheet.CreateSheetRequestDto;
import com.example.salessync.dto.sheet.SheetResponseDto;
import java.util.List;

public interface SheetService {
    SheetResponseDto addSheet(Long userId, CreateSheetRequestDto requestDto);

    List<SheetResponseDto> getAllSheets(Long userId);

    SheetResponseDto getSheetById(Long userId, Long sheetId);

    void deleteSheetById(Long userId, Long sheetId);
}
