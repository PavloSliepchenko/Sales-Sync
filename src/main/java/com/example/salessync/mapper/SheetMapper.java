package com.example.salessync.mapper;

import com.example.salessync.config.MapperConfig;
import com.example.salessync.dto.sheet.SheetResponseDto;
import com.example.salessync.model.Sheet;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = {LineMapper.class, UserMapper.class})
public interface SheetMapper {
    SheetResponseDto toDto(Sheet sheet);
}
