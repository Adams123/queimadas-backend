package com.queimadas.model.mapper;

import com.queimadas.model.FileDB;
import com.queimadas.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    Location toEntity(LocationDTO locationDTO);
    @Mapping(source = "fileDB", target = "image")
    @Mapping(target = "id", ignore = true)
    Location toEntity(LocationDTO locationDTO, FileDB fileDB);
    LocationDTO toDTO(Location location);
}
