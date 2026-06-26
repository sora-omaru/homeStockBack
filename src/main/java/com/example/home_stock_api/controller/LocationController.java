package com.example.home_stock_api.controller;

import com.example.home_stock_api.dto.request.LocationCreateRequestDto;
import com.example.home_stock_api.dto.request.LoginRequestDto;
import com.example.home_stock_api.dto.response.LocationResponseDto;
import com.example.home_stock_api.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/location")
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public List<LocationResponseDto> locations(Authentication authentication) {
        UUID publicId = UUID.fromString(authentication.getName());

        return locationService.getLocations(publicId);

    }

    @PostMapping("/locations")
    public LocationResponseDto createLocation(@Valid @RequestBody LocationCreateRequestDto request, Authentication authentication){
        UUID publicId = UUID.fromString(authentication.getName());

        return  locationService.createLocation(publicId,request);

    }
}
