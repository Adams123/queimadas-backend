package com.queimadas.service;

import com.queimadas.config.security.service.UserDetailsEntityImpl;
import com.queimadas.exception.UserNotFoundException;
import com.queimadas.model.FileDB;
import com.queimadas.model.Location;
import com.queimadas.model.User;
import com.queimadas.model.dto.LocationDTO;
import com.queimadas.model.mapper.LocationMapper;
import com.queimadas.repository.LocationRepository;
import com.queimadas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final LocationMapper mapper;

    public LocationDTO createNewLocation(final MultipartFile image, final LocationDTO locationDTO, final String userId) throws IOException, UserNotFoundException {
        FileDB fileDB = null;
        if (image != null) {
            fileDB = fileStorageService.store(image);
        }
        Location location = mapper.toEntity(locationDTO, fileDB);
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new UserNotFoundException(userId));
        location.setUser(user);
        Location locationEntity = locationRepository.save(location);
        return mapper.toDTO(locationEntity);
    }

    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    public List<UUID> updateSentLocations(UserDetailsEntityImpl details, List<UUID> locations) throws UserNotFoundException {
        List<Location> foundLocations = locationRepository.findAllById(locations);
        User user = userRepository.findById(details.getId()).orElseThrow(() -> new UserNotFoundException(details.getId().toString()));
        user.setLocations(new HashSet<>(foundLocations));
        userRepository.save(user);

        return user.getLocationIds();
    }
}
