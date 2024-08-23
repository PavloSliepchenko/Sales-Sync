package com.example.salessync.mapper;

import com.example.salessync.config.MapperConfig;
import com.example.salessync.dto.size.CreateSizeRequestDto;
import com.example.salessync.dto.size.SizeResponseDto;
import com.example.salessync.model.Size;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface SizeMapper {
    Size toModel(CreateSizeRequestDto requestDto);

    SizeResponseDto toDto(Size size);
}
