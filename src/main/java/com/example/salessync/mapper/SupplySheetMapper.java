package com.example.salessync.mapper;

import com.example.salessync.config.MapperConfig;
import com.example.salessync.dto.sheet.SupplySheetResponseDto;
import com.example.salessync.model.SupplySheet;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = {SupplySheetLineMapper.class, UserMapper.class})
public interface SupplySheetMapper {
    SupplySheetResponseDto toDto(SupplySheet sheet);
}
