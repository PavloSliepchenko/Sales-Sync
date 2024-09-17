package com.example.salessync.mapper;

import com.example.salessync.config.MapperConfig;
import com.example.salessync.dto.cell.CellResponseDto;
import com.example.salessync.dto.cell.CreateCellRequestDto;
import com.example.salessync.model.Cell;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface CellMapper {
    Cell toModel(CreateCellRequestDto requestDto);

    CellResponseDto toDto(Cell cell);
}
