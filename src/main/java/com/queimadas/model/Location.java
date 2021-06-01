package com.queimadas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;

    private Long latitude;
    private Long longitude;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private FileDB image;

    @OneToMany(mappedBy = "id")
    private Set<User> sentUsers;

}
