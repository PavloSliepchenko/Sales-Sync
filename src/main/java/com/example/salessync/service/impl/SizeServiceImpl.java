package com.example.salessync.service.impl;

import com.example.salessync.dto.sheet.SupplySheetResponseDto;
import com.example.salessync.dto.size.CreateSizeRequestDto;
import com.example.salessync.dto.size.SizeResponseDto;
import com.example.salessync.exception.EntityAlreadyExistsException;
import com.example.salessync.exception.EntityNotFoundException;
import com.example.salessync.mapper.SizeMapper;
import com.example.salessync.mapper.SupplySheetMapper;
import com.example.salessync.model.Size;
import com.example.salessync.model.SupplySheet;
import com.example.salessync.model.SupplySheetLine;
import com.example.salessync.model.User;
import com.example.salessync.repository.SizeRepository;
import com.example.salessync.repository.SupplySheetLineRepository;
import com.example.salessync.repository.SupplySheetRepository;
import com.example.salessync.repository.UserRepository;
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
    private final SupplySheetLineRepository lineRepository;
    private final SupplySheetRepository sheetRepository;
    private final SizeRepository sizeRepository;
    private final UserRepository userRepository;
    private final SupplySheetMapper sheetMapper;
    private final SizeMapper sizeMapper;

    @Override
    public SupplySheetResponseDto addSize(Long userId, CreateSizeRequestDto requestDto) {
        SupplySheet sheet = getSheetByUserId(userId);
        Optional<Size> sizeOptional = sizeRepository.findAllByUserId(userId).stream()
                .filter(e -> e.getName().equals(requestDto.name()))
                .findFirst();
        if (sizeOptional.isPresent()) {
            throw new EntityAlreadyExistsException("This size was added before");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no user with id " + userId));
        if (sheet.getLines().size() > 0) {
            sheet.getLines().forEach(e -> {
                List<Size> sizes = e.getSizes();
                Size size = sizeMapper.toModel(requestDto);
                size.setUser(user);
                sizeRepository.save(size);
                sizes.add(size);
            });
            lineRepository.saveAllAndFlush(sheet.getLines());
        } else {
            Size size = sizeMapper.toModel(requestDto);
            size.setUser(user);
            sizeRepository.save(size);
        }
        return sheetMapper.toDto(sheetRepository.save(sheet));
    }

    @Override
    public SizeResponseDto updateSize(
            Long userId, Long lineId, Long sizeId, Integer number
    ) {
        SupplySheet sheet = getSheetByUserId(userId);

        SupplySheetLine line = sheet.getLines().stream()
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
    public void deleteSize(Long userId, String sizeName) {
        SupplySheet sheet = getSheetByUserId(userId);
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

    private SupplySheet getSheetByUserId(Long userId) {
        return sheetRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "User with ID %s doesn't have a supply sheet", userId)));
    }
}
