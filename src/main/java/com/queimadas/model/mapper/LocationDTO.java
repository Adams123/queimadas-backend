package com.queimadas.model.mapper;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class LocationDTO {
    private Double latitude;
    private Double longitude;
}