package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.LocationCreateRequestDto;
import com.example.home_stock_api.dto.response.LocationResponseDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface LocationService {
    List<LocationResponseDto> getLocations(UUID publicId);

    LocationResponseDto createLocation(UUID publicId, @Valid LocationCreateRequestDto request);

    void deleteLocation(UUID publicId, Long locationId);
}
