package com.example.salessync.service.impl;

import com.example.salessync.dto.line.CreateLineRequestDto;
import com.example.salessync.dto.line.LineResponseDto;
import com.example.salessync.exception.EntityNotFoundException;
import com.example.salessync.mapper.LineMapper;
import com.example.salessync.model.Line;
import com.example.salessync.model.Sheet;
import com.example.salessync.model.Size;
import com.example.salessync.repository.LineRepository;
import com.example.salessync.repository.SheetRepository;
import com.example.salessync.service.LineService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LineServiceImpl implements LineService {
    private final SheetRepository sheetRepository;
    private final LineRepository lineRepository;
    private final LineMapper lineMapper;

    @Override
    public LineResponseDto addLine(Long userId, Long sheetId, CreateLineRequestDto requestDto) {
        Line line = new Line();
        line.setArticle(requestDto.getArticle());
        line.setClothType(requestDto.getClothType());
        line.setAge(requestDto.getAge());
        line.setColor(requestDto.getColor());
        line.setSeries(requestDto.getSeries());
        line.setPrice(requestDto.getPrice());
        line.setSupply(requestDto.getSupply());
        line.setBrand(requestDto.getBrand());
        Sheet sheet = getSheetByIdAndUserId(sheetId, userId);
        if (sheet.getLines().size() > 0) {
            List<Size> sizeList = line.getSizes();
            sheet.getLines().get(0).getSizes().forEach(e -> {
                Size size = new Size();
                size.setName(e.getName());
                sizeList.add(size);
            });
        }
        line.setSheet(sheet);
        return lineMapper.toDto(lineRepository.save(line));
    }

    @Override
    public LineResponseDto updateLine(
            Long userId, Long sheetId, Long lineId, CreateLineRequestDto requestDto) {
        Sheet sheet = getSheetByIdAndUserId(sheetId, userId);
        Line line = getLine(sheet, lineId);
        if (requestDto.getAge() != null) {
            line.setAge(requestDto.getAge());
        }
        if (requestDto.getPrice() != null) {
            line.setPrice(requestDto.getPrice());
        }
        if (requestDto.getColor() != null) {
            line.setColor(requestDto.getColor());
        }
        if (requestDto.getBrand() != null) {
            line.setBrand(requestDto.getBrand());
        }
        if (requestDto.getSeries() != null) {
            line.setSeries(requestDto.getSeries());
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
        Sheet sheet = getSheetByIdAndUserId(sheetId, userId);
        Line line = getLine(sheet, lineId);
        lineRepository.delete(line);
    }

    private Sheet getSheetByIdAndUserId(Long sheetId, Long userId) {
        return sheetRepository.findByIdAndUserId(sheetId, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "User with ID %s doesn't have a sheet with ID %s", userId, sheetId)));
    }

    private Line getLine(Sheet sheet, Long lineId) {
        return sheet.getLines().stream()
                .filter(e -> e.getId().equals(lineId))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no line with ID " + lineId));
    }
}
