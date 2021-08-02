package com.queimadas.controller;

import com.queimadas.config.security.service.UserDetailsImpl;
import com.queimadas.model.FileDB;
import com.queimadas.model.Location;
import com.queimadas.model.User;
import com.queimadas.model.mapper.LocationDTO;
import com.queimadas.model.mapper.LocationMapper;
import com.queimadas.repository.LocationRepository;
import com.queimadas.repository.UserRepository;
import com.queimadas.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/location")
//@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
@Slf4j
public class LocationController {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;
    private final LocationMapper mapper;

    public LocationController(LocationRepository locationRepository, UserRepository userRepository, FileStorageService fileStorageService, LocationMapper mapper) {
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
        this.mapper = mapper;
    }

    @PostMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> create(
            @RequestPart LocationDTO locationDTO,
            @RequestPart(required = false) MultipartFile image) throws IOException {
        FileDB fileDB = null;
        if (image != null) {
            fileDB = fileStorageService.store(image);
        }
        Location location = mapper.toEntity(locationDTO, fileDB);
        User user = userRepository.findById(UUID.fromString("0fcf0d94-0b33-4063-8708-ff4e1d7847e4")).get();
        location.setUser(user);
        return ResponseEntity.ok(locationRepository.save(location).getId().toString());
    }

    @Transactional
    @GetMapping("/pendings")
    public ResponseEntity<Set<LocationDTO>> findPendings(@RequestParam UUID userId) {
        User authenticated = userRepository.findById(userId).get();
        List<UUID> userLocations = authenticated.getLocationIds();
        List<UUID> locations = locationRepository.getIds();
        Collection<UUID> missingLocations = CollectionUtils.subtract(locations, userLocations);
        List<Location> foundLocations = locationRepository.findAllById(missingLocations);
        Set<LocationDTO> returnLocations = new HashSet<>();
        for (Location foundLocation : foundLocations) {
            returnLocations.add(mapper.toDTO(foundLocation));
        }
        return ResponseEntity.ok(returnLocations);
    }

    @PutMapping("/sent")
    public ResponseEntity<List<UUID>> updateSent(@RequestBody final List<UUID> locations, final Authentication authentication) {
        final UserDetailsImpl details = (UserDetailsImpl) authentication.getPrincipal();
        List<Location> foundLocations = locationRepository.findAllById(locations);
        User user = userRepository.findById(details.getId()).get();
        user.setLocations(new HashSet<>(foundLocations));
        userRepository.save(user);
        return ResponseEntity.ok(user.getLocationIds());
    }

}
