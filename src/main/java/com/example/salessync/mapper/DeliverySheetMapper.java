package com.example.salessync.mapper;

import com.example.salessync.config.MapperConfig;
import com.example.salessync.dto.sheet.DeliverySheetResponseDto;
import com.example.salessync.model.DeliverySheet;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class, uses = {DeliverySheetLineMapper.class, UserMapper.class})
public interface DeliverySheetMapper {
    DeliverySheetResponseDto toDto(DeliverySheet deliverySheet);
}
