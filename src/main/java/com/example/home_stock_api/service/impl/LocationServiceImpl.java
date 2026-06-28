package com.example.home_stock_api.service.impl;

import com.example.home_stock_api.common.error.BusinessException;
import com.example.home_stock_api.common.error.ErrorCode;
import com.example.home_stock_api.dto.request.LocationCreateRequestDto;
import com.example.home_stock_api.dto.response.LocationResponseDto;
import com.example.home_stock_api.entity.LocationEntity;
import com.example.home_stock_api.entity.UserEntity;
import com.example.home_stock_api.repository.LocationRepository;
import com.example.home_stock_api.repository.UserRepository;
import com.example.home_stock_api.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;


    @Override
    public List<LocationResponseDto> getLocations(UUID publicId) {
        UserEntity user = userRepository.findByPublicId(publicId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        List<LocationEntity> locations = locationRepository.findByUser(user);
        return locations.stream().map(location -> new LocationResponseDto(location.getId(), location.getName())).toList();
    }

    @Override
    public LocationResponseDto createLocation(UUID publicId, LocationCreateRequestDto request) {
        UserEntity user = userRepository.findByPublicId(publicId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        if (locationRepository.existsByUserAndName(user, request.getName())) {
            throw new BusinessException(ErrorCode.LOCATION_ALREADY_EXISTS);
        }

        LocationEntity location = new LocationEntity();
        location.setUser(user);
        location.setName(request.getName());

        LocationEntity savedLocation = locationRepository.save(location);
        return new LocationResponseDto(savedLocation.getId(), savedLocation.getName());
    }

    @Override
    public void deleteLocation(UUID publicId, Long locationId) {
        LocationEntity location = locationRepository
                .findByIdAndUser_PublicId(locationId, publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LOCATION_NOT_FOUND));


        locationRepository.delete(location);
    }
}
