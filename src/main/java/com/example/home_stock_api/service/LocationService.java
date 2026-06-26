package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.response.LocationResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


public interface LocationService {
    List<LocationResponseDto> getLocations(UUID publicId);
}
