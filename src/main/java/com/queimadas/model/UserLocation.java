package com.queimadas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocation {

    @EmbeddedId
    private UserLocationCompositeKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @MapsId("locationId")
    @JoinColumn(name = "location_id")
    Location location;
}
