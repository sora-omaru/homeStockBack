package com.example.home_stock_api.controller;

import com.example.home_stock_api.dto.request.LocationCreateRequestDto;
import com.example.home_stock_api.dto.request.UpdateLocationRequestDto;
import com.example.home_stock_api.dto.response.LocationResponseDto;
import com.example.home_stock_api.security.provider.CurrentUserProvider;
import com.example.home_stock_api.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/locations")
public class LocationController {
    private final LocationService locationService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    public List<LocationResponseDto> locations(Authentication authentication) {
        return locationService.getLocations(currentUserProvider.getPublicId(authentication));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationResponseDto createLocation(@Valid @RequestBody LocationCreateRequestDto request, Authentication authentication) {
        return locationService.createLocation(currentUserProvider.getPublicId(authentication), request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(Authentication authentication, @PathVariable Long id) {
        locationService.deleteLocation(currentUserProvider.getPublicId(authentication), id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public LocationResponseDto updateLocation(Authentication authentication, @PathVariable Long id ,@Valid @RequestBody UpdateLocationRequestDto request){
        return locationService.updateLocation(currentUserProvider.getPublicId(authentication),id,request);
    }
}

