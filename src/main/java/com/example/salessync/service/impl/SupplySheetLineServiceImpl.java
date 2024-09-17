package com.example.salessync.service.impl;

import com.example.salessync.dto.cell.UpdateCellRequestDto;
import com.example.salessync.dto.line.CreateDeliverySheetLineRequestDto;
import com.example.salessync.dto.line.CreateSupplySheetLineRequestDto;
import com.example.salessync.dto.line.SupplySheetLineResponseDto;
import com.example.salessync.exception.EntityAlreadyExistsException;
import com.example.salessync.exception.EntityNotFoundException;
import com.example.salessync.mapper.SupplySheetLineMapper;
import com.example.salessync.model.Cell;
import com.example.salessync.model.SupplySheet;
import com.example.salessync.model.SupplySheetLine;
import com.example.salessync.model.User;
import com.example.salessync.repository.CellRepository;
import com.example.salessync.repository.SupplySheetLineRepository;
import com.example.salessync.repository.SupplySheetRepository;
import com.example.salessync.repository.UserRepository;
import com.example.salessync.service.DeliverySheetLineService;
import com.example.salessync.service.SizeService;
import com.example.salessync.service.SupplySheetLineService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplySheetLineServiceImpl implements SupplySheetLineService {
    private static final Cell.CellType SIZE = Cell.CellType.SIZE;
    private final DeliverySheetLineService deliverySheetLineService;
    private final SupplySheetLineRepository supplyLineRepository;
    private final SupplySheetRepository sheetRepository;
    private final SupplySheetLineMapper lineMapper;
    private final CellRepository cellRepository;
    private final UserRepository userRepository;
    private final SizeService sizeService;

    @Override
    public SupplySheetLineResponseDto addLine(Long userId,
                                              CreateSupplySheetLineRequestDto requestDto) {
        List<Cell> sizes = cellRepository.findAllByUserIdAndCellType(userId, SIZE);
        boolean hasAllSizes = checkSizes(sizes, requestDto);
        if (!hasAllSizes) {
            throw new EntityAlreadyExistsException("Some sizes were not added yet");
        }
        SupplySheet sheet = getSheetByUserId(userId);
        Optional<SupplySheetLine> optionalSupplySheetLine =
                supplyLineRepository.findByArticleAndAgeAndClothTypeAndColorAndBrandAndSheetId(
                        requestDto.getArticle(),
                        requestDto.getAge(),
                        requestDto.getClothType(),
                        requestDto.getColor(),
                        requestDto.getBrand(),
                        sheet.getId()
                );
        if (optionalSupplySheetLine.isEmpty()) {
            SupplySheetLine line = lineMapper.toModel(requestDto);
            line.setSizes(new ArrayList<>());
            if (sheet.getLines().size() > 0) {
                List<Cell> sizeList = line.getSizes();
                User user = userRepository.findById(userId)
                                .orElseThrow(() ->
                                        new EntityNotFoundException(
                                                "There is no user with id " + userId));
                sheet.getLines().get(0).getSizes().forEach(e -> {
                    Cell size = new Cell();
                    size.setName(e.getName());
                    size.setUser(user);
                    size.setCellType(SIZE);
                    sizeList.add(size);
                    cellRepository.save(size);
                });
            } else {
                line.setSizes(sizes);
            }
            line.setSheet(sheet);
            line = supplyLineRepository.save(line);
            sheet.getLines().add(line);
            if (requestDto.getSizes() != null) {
                for (UpdateCellRequestDto sizeDto : requestDto.getSizes()) {
                    Cell size = line.getSizes().stream()
                            .filter(s -> s.getName().equals(sizeDto.getName()))
                            .findFirst()
                            .orElseThrow(() ->
                                    new EntityNotFoundException(
                                            String.format("There is no size %s. Please add it",
                                                    sizeDto.getName())));
                    sizeService.updateSize(
                            userId,
                            line.getId(),
                            size.getId(),
                            sizeDto.getValue()
                    );
                }
            }
            deliverySheetLineService.addLine(
                    userId,
                    new CreateDeliverySheetLineRequestDto(line.getArticle(), line.getSupply())
            );
            return lineMapper.toDto(line);
        }
        SupplySheetLine supplySheetLine = optionalSupplySheetLine.get();
        return mergeLines(supplySheetLine, requestDto);
    }

    @Override
    public SupplySheetLineResponseDto updateLine(
            Long userId, Long lineId, CreateSupplySheetLineRequestDto requestDto) {
        SupplySheet sheet = getSheetByUserId(userId);
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
            deliverySheetLineService.updateSupply(userId, lineId, requestDto.getSupply());
        }
        if (requestDto.getClothType() != null) {
            line.setClothType(requestDto.getClothType());
        }
        return lineMapper.toDto(supplyLineRepository.save(line));
    }

    private SupplySheetLineResponseDto mergeLines(SupplySheetLine line,
                                                  CreateSupplySheetLineRequestDto requestDto) {
        if (requestDto.getSupply() != null) {
            Integer newSupply = line.getSupply() + requestDto.getSupply();
            line.setSupply(newSupply);
            deliverySheetLineService.updateSupply(
                    line.getSheet().getUser().getId(), line.getId(), newSupply);
        }
        if (requestDto.getSizes() != null) {
            for (UpdateCellRequestDto sizeDto : requestDto.getSizes()) {
                Cell size = line.getSizes().stream()
                        .filter(s -> s.getName().equals(sizeDto.getName()))
                        .findFirst()
                        .orElseThrow(() ->
                                new EntityNotFoundException(
                                        String.format("There is no size %s. Please add it",
                                                sizeDto.getName())));
                sizeService.updateSize(
                        line.getSheet().getUser().getId(),
                        line.getId(),
                        size.getId(),
                        sizeDto.getValue() + (size.getValue() == null ? 0 : size.getValue())
                );
            }
        }
        if (requestDto.getSeries() != null) {
            line.setSeries(requestDto.getSeries());
            line = updateLineData(line);
        }
        if (requestDto.getPrice() != null) {
            line.setPrice(requestDto.getPrice());
            line = updateLineData(line);
        }
        return lineMapper.toDto(supplyLineRepository.save(line));
    }

    @Override
    public void deleteLine(Long userId, Long lineId) {
        SupplySheet sheet = getSheetByUserId(userId);
        SupplySheetLine line = getLine(sheet, lineId);
        supplyLineRepository.delete(line);
    }

    private boolean checkSizes(List<Cell> sizes, CreateSupplySheetLineRequestDto requestDto) {
        List<String> existingSizeNames = sizes.stream()
                .map(Cell::getName)
                .toList();

        List<String> dtoSizeNames = requestDto.getSizes().stream()
                .map(UpdateCellRequestDto::getName)
                .toList();
        return new HashSet<>(existingSizeNames).containsAll(dtoSizeNames);
    }

    private SupplySheet getSheetByUserId(Long userId) {
        return sheetRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        "User with ID %s doesn't have a supply sheet", userId)));
    }

    private SupplySheetLine getLine(SupplySheet sheet, Long lineId) {
        return sheet.getLines().stream()
                .filter(e -> e.getId().equals(lineId))
                .findFirst()
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no line with ID " + lineId));
    }

    private SupplySheetLine updateLineData(SupplySheetLine line) {
        line.setTotalQuantity(line.getQuantity() * line.getSeries());
        line.setTotalPrice(line.getPrice().multiply(BigDecimal.valueOf(line.getTotalQuantity())));
        return line;
    }
}
