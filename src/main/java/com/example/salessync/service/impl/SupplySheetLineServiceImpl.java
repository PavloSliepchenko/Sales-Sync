package com.example.salessync.service.impl;

import com.example.salessync.dto.line.CreateSupplySheetLineRequestDto;
import com.example.salessync.dto.line.SupplySheetLineResponseDto;
import com.example.salessync.exception.EntityNotFoundException;
import com.example.salessync.mapper.SupplySheetLineMapper;
import com.example.salessync.model.Size;
import com.example.salessync.model.SupplySheet;
import com.example.salessync.model.SupplySheetLine;
import com.example.salessync.repository.SizeRepository;
import com.example.salessync.repository.SupplySheetLineRepository;
import com.example.salessync.repository.SupplySheetRepository;
import com.example.salessync.service.SupplySheetLineService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplySheetLineServiceImpl implements SupplySheetLineService {
    private final SupplySheetRepository sheetRepository;
    private final SupplySheetLineRepository lineRepository;
    private final SizeRepository sizeRepository;
    private final SupplySheetLineMapper lineMapper;

    @Override
    public SupplySheetLineResponseDto addLine(Long userId,
                                              Long sheetId,
                                              CreateSupplySheetLineRequestDto requestDto) {
        SupplySheetLine line = new SupplySheetLine();
        line.setArticle(requestDto.getArticle());
        line.setClothType(requestDto.getClothType());
        line.setAge(requestDto.getAge());
        line.setColor(requestDto.getColor());
        line.setSeries(requestDto.getSeries());
        line.setPrice(requestDto.getPrice());
        line.setSupply(requestDto.getSupply());
        line.setBrand(requestDto.getBrand());
        line.setSizes(new ArrayList<>());
        SupplySheet sheet = getSheetByIdAndUserId(sheetId, userId);
        if (sheet.getLines().size() > 0) {
            List<Size> sizeList = line.getSizes();
            sheet.getLines().get(0).getSizes().forEach(e -> {
                Size size = new Size();
                size.setName(e.getName());
                sizeList.add(size);
                sizeRepository.save(size);
            });
        }
        line.setSheet(sheet);
        return lineMapper.toDto(lineRepository.save(line));
    }

    @Override
    public SupplySheetLineResponseDto updateLine(
            Long userId, Long sheetId, Long lineId, CreateSupplySheetLineRequestDto requestDto) {
        SupplySheet sheet = getSheetByIdAndUserId(sheetId, userId);
        SupplySheetLine line = getLine(sheet, lineId);
        if (requestDto.getAge() != null) {
            line.setAge(requestDto.getAge());
        }
        if (requestDto.getPrice() != null) {
            line.setPrice(requestDto.getPrice());
            if (line.getTotalQuantity() != null) {
                line.setTotalPrice(
                        BigDecimal.valueOf(line.getTotalQuantity()).multiply(line.getPrice()));
            }
        }
        if (requestDto.getColor() != null) {
            line.setColor(requestDto.getColor());
        }
        if (requestDto.getBrand() != null) {
            line.setBrand(requestDto.getBrand());
        }
        if (requestDto.getSeries() != null) {
            line.setSeries(requestDto.getSeries());
            if (line.getQuantity() != null) {
                line.setTotalQuantity(line.getQuantity() * line.getSeries());
                line.setTotalPrice(
                        BigDecimal.valueOf(line.getTotalQuantity()).multiply(line.getPrice()));
            }
        }
        if (requestDto.getArticle() != null) {
            line.setArticle(requestDto.getArticle());
        }
        if (requestDto.getSupply() != null) {
            line.setSupply(requestDto.getSupply());
        }
        if (requestDto.getClothType() != null) {
            line.setClothType(requestDto.getClothType());
        }
        return lineMapper.toDto(lineRepository.save(line));
    }

    @Override
    public void deleteLine(Long userId, Long sheetId, Long lineId) {
        SupplySheet sheet = getSheetByIdAndUserId(sheetId, userId);
        SupplySheetLine line = getLine(sheet, lineId);
        lineRepository.delete(line);
    }

    private SupplySheet getSheetByIdAndUserId(Long sheetId, Long userId) {
        return sheetRepository.findByIdAndUserId(sheetId, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "User with ID %s doesn't have a sheet with ID %s", userId, sheetId)));
    }

    private SupplySheetLine getLine(SupplySheet sheet, Long lineId) {
        return sheet.getLines().stream()
                .filter(e -> e.getId().equals(lineId))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no line with ID " + lineId));
    }
}
