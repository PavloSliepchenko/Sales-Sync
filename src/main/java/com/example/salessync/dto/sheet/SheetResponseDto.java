package com.example.salessync.dto.sheet;

import com.example.salessync.dto.line.LineResponseDto;
import com.example.salessync.dto.user.UserResponseDto;
import java.util.List;
import lombok.Data;

@Data
public class SheetResponseDto {
    private Long id;
    private String name;
    private UserResponseDto user;
    private List<LineResponseDto> lines;
}
