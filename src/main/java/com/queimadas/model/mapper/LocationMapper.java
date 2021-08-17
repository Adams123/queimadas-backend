package com.queimadas.model.mapper;

import com.queimadas.model.FileDB;
import com.queimadas.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    @Mapping(source = "fileDB", target = "image")
    @Mapping(target = "id", ignore = true)
    Location toEntity(LocationDTO locationDTO, FileDB fileDB);

    LocationDTO toDTO(Location location);

    Set<LocationDTO> toDTO(List<Location> location);

    @Mapping(target = "username", source = "location.user.username")
    @Mapping(target = "userEmail", source = "location.user.email")
    @Mapping(target = "detectionDate", source = "location.detectionTime")
    LocationInformationDTO toDto(Location location);
}
