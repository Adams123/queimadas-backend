package com.queimadas.controller;

import com.queimadas.config.security.service.UserDetailsImpl;
import com.queimadas.model.FileDB;
import com.queimadas.model.Location;
import com.queimadas.model.User;
import com.queimadas.model.mapper.LocationDTO;
import com.queimadas.model.mapper.LocationInformationDTO;
import com.queimadas.model.mapper.LocationMapper;
import com.queimadas.repository.LocationRepository;
import com.queimadas.repository.UserRepository;
import com.queimadas.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

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
            @RequestPart(required = false) MultipartFile image,
            @RequestParam(defaultValue = "0fcf0d94-0b33-4063-8708-ff4e1d7847e4") String userId) throws IOException {
        FileDB fileDB = null;
        if (image != null) {
            fileDB = fileStorageService.store(image);
        }
        Location location = mapper.toEntity(locationDTO, fileDB);
        userId = userId.replaceAll("\"", "");
        User user = userRepository.findById(UUID.fromString(userId)).get();
        location.setUser(user);
        return ResponseEntity.ok(locationRepository.save(location).getId().toString());
    }

    @Transactional
    @GetMapping("/pendings")
    public ResponseEntity<Set<LocationDTO>> findPendings() {
        List<Location> foundLocations = locationRepository.findAll();
        return ResponseEntity.ok(mapper.toDTO(foundLocations));
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

    @GetMapping("/locationInfo")
    public ResponseEntity<Set<LocationInformationDTO>> getLocations() {
        List<Location> locations = locationRepository.findAll();
        Set<LocationInformationDTO> list = new HashSet<>();
        for (Location location : locations) {
            list.add(mapper.toDto(location));
        }
        return ResponseEntity.ok(list);
    }

}
