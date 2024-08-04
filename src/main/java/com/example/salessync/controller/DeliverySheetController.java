package com.example.salessync.controller;

import com.example.salessync.dto.sheet.DeliverySheetResponseDto;
import com.example.salessync.model.User;
import com.example.salessync.service.DeliverySheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/deliverySheets")
@Tag(name = "Delivery sheets management",
        description = "Provides endpoints for CRUD operations with delivery sheets")
public class DeliverySheetController {
    private final DeliverySheetService sheetService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get delivery sheet",
            description = "Allows to get sheet by user ID. Available to all authenticated users")
    public DeliverySheetResponseDto getSheetById(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return sheetService.getSheet(user.getId());
    }
}
