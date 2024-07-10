package com.example.salessync.mapper;

import com.example.salessync.config.MapperConfig;
import com.example.salessync.dto.line.CreateLineRequestDto;
import com.example.salessync.dto.line.LineResponseDto;
import com.example.salessync.model.Line;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = SizeMapper.class)
public interface LineMapper {
    Line toModel(CreateLineRequestDto createLineRequestDto);

    LineResponseDto toDto(Line line);
}
