package com.ufscar.queimadas.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table
@NoArgsConstructor
public class UserBearerToken {

    @Id
    private UUID token;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    private boolean expired;
    private LocalDateTime validUntil;

    public UserBearerToken(User user, String token) {
        setUser(user);
        setToken(UUID.fromString(token));
        validUntil = LocalDateTime.now().plusDays(7);
    }

    public UserBearerToken(User user, UUID token) {
        setUser(user);
        setToken(token);
        validUntil = LocalDateTime.now().plusDays(7);
    }

    @Override
    public String toString() {
        return String.format("Token %s for user %s", getToken().toString(), getUser().getId().toString());
    }
}
