package com.queimadas.controller;

import com.queimadas.model.FileDB;
import com.queimadas.model.Location;
import com.queimadas.model.mapper.LocationDTO;
import com.queimadas.model.mapper.LocationMapper;
import com.queimadas.repository.LocationRepository;
import com.queimadas.repository.UserRepository;
import com.queimadas.service.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/location")
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
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
    public ResponseEntity<String> create(@RequestPart LocationDTO locationDTO, @RequestPart MultipartFile image) throws IOException {
        FileDB fileDB = fileStorageService.store(image);
        Location location = mapper.toEntity(locationDTO, fileDB);
        return ResponseEntity.ok(locationRepository.save(location).getId().toString());
    }

    @GetMapping("/pendings")
    public ResponseEntity<List<Location>> findPendings(@RequestParam UUID userId) {
        return ResponseEntity.ok(locationRepository.findAllBySentUsersNotContaining(userRepository.findById(userId).get()));
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<FileDB> findFile(@PathVariable UUID id) {
        return ResponseEntity.ok(fileStorageService.getFile(id));
    }


}
