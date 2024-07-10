package com.example.salessync.service;

import com.example.salessync.dto.line.CreateLineRequestDto;
import com.example.salessync.dto.line.LineResponseDto;

public interface LineService {
    LineResponseDto addLine(Long userId, Long sheetId, CreateLineRequestDto requestDto);

    LineResponseDto updateLine(
            Long userId, Long sheetId, Long lineId, CreateLineRequestDto requestDto);

    void deleteLine(Long userId, Long sheetId, Long lineId);
}
