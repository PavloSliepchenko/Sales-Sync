package com.example.salessync.controller;

import com.example.salessync.dto.sheet.SupplySheetResponseDto;
import com.example.salessync.model.User;
import com.example.salessync.service.SupplySheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sheets")
@Tag(name = "Sheets management",
        description = "Provides endpoints for CRUD operations with sheets")
public class SupplySheetController {
    private final SupplySheetService sheetService;

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get all sheets",
            description = "Allows to get all sheets. Available to all authenticated users")
    public List<SupplySheetResponseDto> getAllUsersSheets(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return sheetService.getAllSheets(user.getId());
    }

    @GetMapping(value = "/{sheetId}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get sheet by ID",
            description = "Allows to get sheet by ID. Available to all authenticated users")
    public SupplySheetResponseDto getSheetById(Authentication authentication,
                                               @PathVariable Long sheetId) {
        User user = (User) authentication.getPrincipal();
        return sheetService.getSheetById(user.getId(), sheetId);
    }
}
