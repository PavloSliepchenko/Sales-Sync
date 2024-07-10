package com.example.salessync.service.impl;

import com.example.salessync.dto.sheet.SheetResponseDto;
import com.example.salessync.dto.size.CreateSizeRequestDto;
import com.example.salessync.dto.size.SizeResponseDto;
import com.example.salessync.exception.EntityAlreadyExistsException;
import com.example.salessync.exception.EntityNotFoundException;
import com.example.salessync.mapper.SheetMapper;
import com.example.salessync.mapper.SizeMapper;
import com.example.salessync.model.Line;
import com.example.salessync.model.Sheet;
import com.example.salessync.model.Size;
import com.example.salessync.repository.LineRepository;
import com.example.salessync.repository.SheetRepository;
import com.example.salessync.repository.SizeRepository;
import com.example.salessync.service.SizeService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SizeServiceImpl implements SizeService {
    private final SheetRepository sheetRepository;
    private final SizeRepository sizeRepository;
    private final LineRepository lineRepository;
    private final SheetMapper sheetMapper;
    private final SizeMapper sizeMapper;

    @Override
    public SheetResponseDto addSize(Long userId, Long sheetId, CreateSizeRequestDto requestDto) {
        Sheet sheet = getSheetByIdAndUserId(sheetId, userId);
        Optional<String> nameOptional = sheet.getLines().get(0).getSizes().stream()
                .map(Size::getName)
                .filter(e -> e.equals(requestDto.name()))
                .findFirst();
        if (nameOptional.isPresent()) {
            throw new EntityAlreadyExistsException("This size was added before");
        }
        sheet.getLines().forEach(e -> {
            List<Size> sizes = e.getSizes();
            Size size = new Size();
            size.setName(requestDto.name());
            sizeRepository.save(size);
            sizes.add(size);
        });
        lineRepository.saveAllAndFlush(sheet.getLines());
        return sheetMapper.toDto(sheetRepository.save(sheet));
    }

    @Override
    public SizeResponseDto updateSize(
            Long userId, Long sheetId, Long lineId, Long sizeId, Integer number
    ) {
        Sheet sheet = getSheetByIdAndUserId(sheetId, userId);

        Line line = sheet.getLines().stream()
                .filter(e -> e.getId().equals(lineId))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no line with ID " + lineId));

        Size size = line.getSizes().stream()
                .filter(e -> e.getId().equals(sizeId))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no size with ID " + sizeId));
        size.setNumber(number);
        int quantity = line.getSizes().stream()
                .map(Size::getNumber)
                .filter(Objects::nonNull)
                .mapToInt(Integer::intValue)
                .sum();
        line.setQuantity(quantity);
        line.setTotalQuantity(quantity * line.getSeries());
        line.setTotalPrice(BigDecimal.valueOf(line.getTotalQuantity()).multiply(line.getPrice()));
        size = sizeRepository.save(size);
        lineRepository.save(line);
        return sizeMapper.toDto(size);
    }

    @Override
    public void deleteSize(Long userId, Long sheetId, String sizeName) {
        Sheet sheet = getSheetByIdAndUserId(sheetId, userId);
        sheet.getLines().forEach(e -> {
            List<Size> sizes = e.getSizes();
            Optional<Size> sizeOptional = sizes.stream()
                    .filter(s -> s.getName().equals(sizeName))
                    .findFirst();
            if (sizeOptional.isPresent()) {
                Size size = sizeOptional.get();
                e.setQuantity(e.getQuantity() - size.getNumber());
                e.setTotalQuantity(e.getQuantity() * e.getSeries());
                e.setTotalPrice(BigDecimal.valueOf(e.getTotalQuantity()).multiply(e.getPrice()));
                sizes.remove(size);
                sizeRepository.delete(size);
            }
        });
        lineRepository.saveAllAndFlush(sheet.getLines());
    }

    private Sheet getSheetByIdAndUserId(Long sheetId, Long userId) {
        return sheetRepository.findByIdAndUserId(sheetId, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "User with ID %s doesn't have a sheet with ID %s", userId, sheetId)));
    }
}
