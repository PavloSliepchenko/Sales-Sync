package com.example.salessync.service.impl;

import com.example.salessync.dto.line.CreateDeliverySheetLineRequestDto;
import com.example.salessync.dto.line.DeliverySheetLineResponseDto;
import com.example.salessync.dto.line.UpdateDeliverySheetLineRequestDto;
import com.example.salessync.exception.EntityNotFoundException;
import com.example.salessync.mapper.DeliverySheetLineMapper;
import com.example.salessync.model.DeliverySheet;
import com.example.salessync.model.DeliverySheetLine;
import com.example.salessync.repository.DeliverySheetLineRepository;
import com.example.salessync.repository.DeliverySheetRepository;
import com.example.salessync.service.DeliverySheetLineService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliverySheetLineServiceImpl implements DeliverySheetLineService {
    private final DeliverySheetLineRepository lineRepository;
    private final DeliverySheetRepository sheetRepository;
    private final DeliverySheetLineMapper lineMapper;

    @Override
    public DeliverySheetLineResponseDto addLine(
            Long userId, CreateDeliverySheetLineRequestDto requestDto) {
        DeliverySheetLine line = lineMapper.toModel(requestDto);
        DeliverySheet sheet = getSheetByUserId(userId);
        line.setSheet(sheet);
        return lineMapper.toDto(lineRepository.save(line));
    }

    @Override
    public DeliverySheetLineResponseDto updateLine(
            Long userId, Long lineId, UpdateDeliverySheetLineRequestDto requestDto) {
        DeliverySheet sheet = getSheetByUserId(userId);
        DeliverySheetLine line = getLine(sheet, lineId);
        if (requestDto.getDeliveryDate() != null) {
            line.setDeliveryDate(requestDto.getDeliveryDate());
        }
        if (requestDto.getWeight() != null) {
            line.setWeight(requestDto.getWeight());
            line = updateLineData(line);
        }
        if (requestDto.getPriceUsd() != null) {
            line.setPriceUsd(requestDto.getPriceUsd());
            line = updateLineData(line);
        }
        if (requestDto.getAdditionalPriceUsd() != null) {
            line.setAdditionalPriceUsd(requestDto.getAdditionalPriceUsd());
            line = updateLineData(line);
        }
        if (requestDto.getDeliveryPriceLocal() != null) {
            line.setDeliveryPriceLocal(requestDto.getDeliveryPriceLocal());
            line = updateLineData(line);
        }
        if (requestDto.getCurrency() != null) {
            line.setCurrency(requestDto.getCurrency());
            line = updateLineData(line);
        }
        return lineMapper.toDto(lineRepository.save(line));
    }

    @Override
    public void deleteLine(Long userId, Long lineId) {
        DeliverySheet sheet = getSheetByUserId(userId);
        DeliverySheetLine line = getLine(sheet, lineId);
        lineRepository.delete(line);
    }

    @Override
    public DeliverySheetLineResponseDto updateSupply(Long userId, Long lineId, Integer supply) {
        DeliverySheet sheet = getSheetByUserId(userId);
        DeliverySheetLine line = getLine(sheet, lineId);
        line.setSupply(supply);
        return lineMapper.toDto(lineRepository.save(updateLineData(line)));
    }

    private DeliverySheet getSheetByUserId(Long userId) {
        return sheetRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(
                                "User with ID %s doesn't have a delivery sheet", userId)));
    }

    private DeliverySheetLine getLine(DeliverySheet sheet, Long lineId) {
        return sheet.getLines().stream()
                .filter(e -> e.getId().equals(lineId))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no line with ID " + lineId));
    }

    private DeliverySheetLine updateLineData(DeliverySheetLine line) {
        if (line.getPriceUsd() != null) {
            line.setTotalPriceUsd(line.getPriceUsd()
                    .multiply(BigDecimal.valueOf(line.getWeight()))
                    .add(line.getAdditionalPriceUsd()));
            line.setDeliveryPricePerUnitUsd(line.getTotalPriceUsd()
                    .divide(BigDecimal.valueOf(line.getSupply()), 2, RoundingMode.HALF_UP));
            line.setAveragePriceLocal((line.getDeliveryPricePerUnitUsd()
                    .multiply(line.getCurrency()))
                    .add(line.getDeliveryPricePerUnitLocal()));
        }
        return line;
    }
}
