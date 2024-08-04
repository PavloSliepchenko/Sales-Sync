package com.example.salessync.controller;

import com.example.salessync.dto.line.CreateDeliverySheetLineRequestDto;
import com.example.salessync.dto.line.DeliverySheetLineResponseDto;
import com.example.salessync.model.User;
import com.example.salessync.service.DeliverySheetLineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/deliveryLines")
@Tag(name = "Delivery sheet lines management",
        description = "Provides endpoints for CRUD operations with delivery sheet lines")
public class DeliverySheetLineController {
    private final DeliverySheetLineService lineService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new delivery sheet line",
            description = "Allows to add a new line. Available to all authenticated users")
    public DeliverySheetLineResponseDto addLine(
            Authentication authentication,
            @Valid @RequestBody CreateDeliverySheetLineRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return lineService.addLine(user.getId(), requestDto);
    }

    @PatchMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Update a delivery sheet line",
            description = "Allows to update (patch) line. Available to all authenticated users")
    public DeliverySheetLineResponseDto updateLine(
            Authentication authentication,
            @RequestParam Long lineId,
            @RequestBody CreateDeliverySheetLineRequestDto requestDto
    ) {
        User user = (User) authentication.getPrincipal();
        return lineService.updateLine(user.getId(), lineId, requestDto);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a delivery sheet line by ID",
            description = "Allows to delete line by ID. Available to all authenticated users")
    public void deleteLine(Authentication authentication,
                           @RequestParam Long lineId) {
        User user = (User) authentication.getPrincipal();
        lineService.deleteLine(user.getId(), lineId);
    }
}
