package com.example.salessync.mapper;

import com.example.salessync.config.MapperConfig;
import com.example.salessync.dto.line.CreateSupplySheetLineRequestDto;
import com.example.salessync.dto.line.SupplySheetLineResponseDto;
import com.example.salessync.model.SupplySheetLine;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = SizeMapper.class)
public interface SupplySheetLineMapper {
    SupplySheetLine toModel(CreateSupplySheetLineRequestDto createLineRequestDto);

    SupplySheetLineResponseDto toDto(SupplySheetLine line);
}
