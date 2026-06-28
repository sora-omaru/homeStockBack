package com.example.home_stock_api.controller;

import com.example.home_stock_api.dto.request.LocationCreateRequestDto;
import com.example.home_stock_api.dto.response.LocationResponseDto;
import com.example.home_stock_api.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public List<LocationResponseDto> locations(Authentication authentication) {
        UUID publicId = UUID.fromString(authentication.getName());

        return locationService.getLocations(publicId);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)//201
    public LocationResponseDto createLocation(@Valid @RequestBody LocationCreateRequestDto request, Authentication authentication) {
        UUID publicId = UUID.fromString(authentication.getName());

        return locationService.createLocation(publicId, request);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(Authentication authentication, @PathVariable Long id) {
        UUID publicId = UUID.fromString(authentication.getName());

        locationService.deleteLocation(publicId, id);

        return ResponseEntity.noContent().build();
    }
}
