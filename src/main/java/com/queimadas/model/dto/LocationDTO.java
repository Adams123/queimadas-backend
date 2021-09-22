package com.queimadas.model.dto;

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