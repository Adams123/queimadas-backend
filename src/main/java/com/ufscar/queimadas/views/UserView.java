package com.ufscar.queimadas.views;

import com.ufscar.queimadas.model.Roles;
import com.ufscar.queimadas.model.User;
import org.springframework.data.rest.core.config.Projection;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Projection(name = "UserView", types = User.class)
public interface UserView {
    UUID getId();
    String getName();
    LocalDateTime getCreationDate();
    Set<Roles> getRoles();
}
