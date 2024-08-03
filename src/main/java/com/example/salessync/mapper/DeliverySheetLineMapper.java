package com.example.salessync.mapper;

import com.example.salessync.config.MapperConfig;
import com.example.salessync.dto.line.CreateDeliverySheetLineRequestDto;
import com.example.salessync.dto.line.DeliverySheetLineResponseDto;
import com.example.salessync.model.DeliverySheetLine;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface DeliverySheetLineMapper {
    DeliverySheetLineResponseDto toDto(DeliverySheetLine line);

    DeliverySheetLine toModel(CreateDeliverySheetLineRequestDto requestDto);
}
