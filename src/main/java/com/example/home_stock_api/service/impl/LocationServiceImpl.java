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

import javax.xml.stream.Location;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Override
    public List<LocationResponseDto> getLocations(UUID publicId) {
        UserEntity user = findUser(publicId);

        return locationRepository.findByUser(user).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LocationResponseDto createLocation(UUID publicId, LocationCreateRequestDto request) {
        UserEntity user = findUser(publicId);

        if (locationRepository.existsByUserAndName(user, request.getName())) {
            throw new BusinessException(ErrorCode.LOCATION_ALREADY_EXISTS);
        }

        LocationEntity savedLocation = locationRepository.save(createLocation(user, request.getName()));

        return toResponse(savedLocation);
    }

    @Override
    public void deleteLocation(UUID publicId, Long locationId) {
        LocationEntity location = findLocation(publicId, locationId);

        locationRepository.delete(location);
    }

    private LocationEntity findLocation(UUID publicId, Long locationId) {
        return locationRepository
                .findByIdAndUser_PublicId(locationId, publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.LOCATION_NOT_FOUND));
    }

    private UserEntity findUser(UUID publicId) {
        return userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private LocationEntity createLocation(UserEntity user, String name) {
        LocationEntity location = new LocationEntity();
        location.setUser(user);
        location.setName(name);

        return location;
    }

    private LocationResponseDto toResponse(LocationEntity location) {
        return new LocationResponseDto(location.getId(), location.getName());
    }
}
