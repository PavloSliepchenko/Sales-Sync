package com.example.salessync.controller;

import com.example.salessync.dto.cell.CellResponseDto;
import com.example.salessync.dto.cell.CreateCellRequestDto;
import com.example.salessync.dto.sheet.SupplySheetResponseDto;
import com.example.salessync.model.User;
import com.example.salessync.service.SizeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sizes")
@Tag(name = "Sizes management",
        description = "Provides endpoints for CRUD operations with sizes")
public class SizeController {
    private final SizeService sizeService;

    @PostMapping
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new size",
            description = "Allows to add a new size to all lines on the sheet. "
                    + "Available to all authenticated users")
    public SupplySheetResponseDto addSize(Authentication authentication,
                                          @Valid @RequestBody CreateCellRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return sizeService.addSize(user.getId(), requestDto);
    }

    @PutMapping(value = "/{value}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Update size value",
            description = "Allows to update a specific size value. "
                    + "Available to all authenticated users")
    public CellResponseDto updateSize(Authentication authentication,
                                      @RequestParam Long lineId,
                                      @RequestParam Long sizeId,
                                      @PathVariable Integer value) {
        User user = (User) authentication.getPrincipal();
        return sizeService.updateSize(user.getId(), lineId, sizeId, value);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('USER')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete size",
            description = "Allows to delete a specific size from all lines on a chosen sheet. "
                    + "Available to all authenticated users")
    public void deleteSize(Authentication authentication,
                           @RequestParam String sizeName) {
        User user = (User) authentication.getPrincipal();
        sizeService.deleteSize(user.getId(), sizeName);
    }
}
