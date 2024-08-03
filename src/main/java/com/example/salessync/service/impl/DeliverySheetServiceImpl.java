package com.example.salessync.service.impl;

import com.example.salessync.dto.sheet.DeliverySheetResponseDto;
import com.example.salessync.exception.EntityNotFoundException;
import com.example.salessync.mapper.DeliverySheetMapper;
import com.example.salessync.model.DeliverySheet;
import com.example.salessync.model.User;
import com.example.salessync.repository.DeliverySheetRepository;
import com.example.salessync.repository.UserRepository;
import com.example.salessync.service.DeliverySheetService;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliverySheetServiceImpl implements DeliverySheetService {
    private static final String DELIVERY = "Delivery";
    private final DeliverySheetRepository sheetRepository;
    private final DeliverySheetMapper sheetMapper;
    private final UserRepository userRepository;

    @Override
    public DeliverySheetResponseDto addSheet(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("There is no user with ID " + userId));
        DeliverySheet deliverySheet = new DeliverySheet();
        deliverySheet.setName(DELIVERY);
        deliverySheet.setUser(user);
        deliverySheet.setLines(new ArrayList<>());
        return sheetMapper.toDto(sheetRepository.save(deliverySheet));
    }

    @Override
    public DeliverySheetResponseDto getSheet(Long userId) {
        return sheetMapper.toDto(getSheetByUserId(userId));
    }

    @Override
    public void deleteSheetByUserId(Long userId) {
        sheetRepository.delete(getSheetByUserId(userId));
    }

    private DeliverySheet getSheetByUserId(Long userId) {
        return sheetRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(
                                "User with ID %s doesn't have a supply sheet", userId)));
    }
}
