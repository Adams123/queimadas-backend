package com.queimadas.controller;

import com.queimadas.config.security.service.UserDetailsEntityImpl;
import com.queimadas.exception.UserNotFoundException;
import com.queimadas.model.Location;
import com.queimadas.model.dto.LocationDTO;
import com.queimadas.model.dto.LocationInformationDTO;
import com.queimadas.model.mapper.LocationMapper;
import com.queimadas.service.LocationService;
import lombok.RequiredArgsConstructor;
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
//@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')") //TODO descomentar quando mandar header do security
@Slf4j
@RequiredArgsConstructor
public class LocationController {

    private final LocationMapper mapper;
    private final LocationService locationService;

    @PostMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> create(
            @RequestPart LocationDTO locationDTO,
            @RequestPart(required = false) MultipartFile image,
            @RequestParam String userId) throws IOException, UserNotFoundException { //TODO remover userId quando mandar header do security
        locationService.createNewLocation(image, locationDTO, userId.replaceAll("\"", ""));
        return ResponseEntity.ok().build();
    }

    @Transactional
    @GetMapping("/pendings")
    public ResponseEntity<Set<LocationDTO>> findPendings() {
        List<Location> foundLocations = locationService.getLocations();
        return ResponseEntity.ok(mapper.toDTO(foundLocations));
    }

    @PutMapping("/sent")
    public ResponseEntity<List<UUID>> updateSent(@RequestBody final List<UUID> locations, final Authentication authentication)
            throws UserNotFoundException {
        final UserDetailsEntityImpl details = (UserDetailsEntityImpl) authentication.getPrincipal();
        return ResponseEntity.ok(locationService.updateSentLocations(details, locations));
    }

    @GetMapping("/locationInfo")
    public ResponseEntity<Set<LocationInformationDTO>> getLocations() {
        List<Location> locations = locationService.getLocations();
        Set<Location> unduplicated = new HashSet<>(locations);
        return ResponseEntity.ok(mapper.toInformationDTO(unduplicated));
    }

}
