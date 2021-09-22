package com.queimadas.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@ToString
@EqualsAndHashCode
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

    @Column(precision = 10, scale = 5)
    private Double latitude;
    @Column(precision = 10, scale = 5)
    private Double longitude;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private FileDB image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    private UUID userCreated;
    private LocalDateTime detectionTime;

    @JsonIgnore
    public User getUser() {
        return this.user;
    }

    public UUID getUser_id() {
        return this.user.getId();
    }

    @PrePersist
    public void setData() {
        detectionTime = LocalDateTime.now();
        if (user != null)
            userCreated = user.getId();
        else userCreated = UUID.fromString("0fcf0d94-0b33-4063-8708-ff4e1d7847e4");
    }

}
