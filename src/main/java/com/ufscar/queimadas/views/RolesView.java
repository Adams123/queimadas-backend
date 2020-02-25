package com.ufscar.queimadas.views;

import com.ufscar.queimadas.model.Roles;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "RolesView", types = Roles.class)
public interface RolesView {
    String getUserRole();
}
