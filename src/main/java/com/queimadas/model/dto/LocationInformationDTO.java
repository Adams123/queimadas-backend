package com.queimadas.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class LocationInformationDTO {
    private Double latitude;
    private Double longitude;
    private LocalDateTime detectionDate;
    private String username;
    private String userEmail;
}