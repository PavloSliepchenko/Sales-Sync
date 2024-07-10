package com.example.salessync.controller;

import com.example.salessync.dto.sheet.CreateSheetRequestDto;
import com.example.salessync.dto.sheet.SheetResponseDto;
import com.example.salessync.model.User;
import com.example.salessync.service.SheetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sheets")
@Tag(name = "Sheets management",
        description = "Provides endpoints for CRUD operations with sheets")
public class SheetController {
    private final SheetService sheetService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new sheet",
            description = "Allows to add a new sheet. Available to all authenticated users")
    public SheetResponseDto addSheet(Authentication authentication,
                                     @Valid @RequestBody CreateSheetRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return sheetService.addSheet(user.getId(), requestDto);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get all sheets",
            description = "Allows to get all sheets. Available to all authenticated users")
    public List<SheetResponseDto> getAllUsersSheets(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return sheetService.getAllSheets(user.getId());
    }

    @GetMapping(value = "/{sheetId}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Get sheet by ID",
            description = "Allows to get sheet by ID. Available to all authenticated users")
    public SheetResponseDto getSheetById(Authentication authentication,
                                         @PathVariable Long sheetId) {
        User user = (User) authentication.getPrincipal();
        return sheetService.getSheetById(user.getId(), sheetId);
    }

    @PutMapping(value = "/{sheetId}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Update sheet name",
            description = "Allows to update sheet name. Available to all authenticated users")
    public SheetResponseDto updateSheetName(Authentication authentication,
                                            @PathVariable Long sheetId,
                                            @Valid @RequestBody CreateSheetRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return sheetService.updateSheetName(user.getId(), sheetId, requestDto);
    }

    @DeleteMapping(value = "/{sheetId}")
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete sheet by ID",
            description = "Allows to delete sheet by ID. Available to all authenticated users")
    public void deleteSheetById(Authentication authentication,
                                @PathVariable Long sheetId) {
        User user = (User) authentication.getPrincipal();
        sheetService.deleteSheetById(user.getId(), sheetId);
    }
}
