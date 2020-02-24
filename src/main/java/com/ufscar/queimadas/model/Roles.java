package com.ufscar.queimadas.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Set;

@Data
@Entity
public class Roles implements GrantedAuthority {
    @Transient
    private long serialVersion = 1L;
    @Id
    private String userRole;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    public Roles(String role) {
        this.userRole = role;
    }

    public Roles(Role role) {
        this.userRole = role.name();
    }

    public Roles() {
        this.userRole = Role.PUBLIC.name();
    }

    @Override
    public String getAuthority() {
        return getUserRole();
    }

    private void setRole(Role role) {
        setRole(role.name());
    }

    private void setRole(String role) {
        this.userRole = role;
    }

    public static Roles fromString(String role) {
        return new Roles(role);
    }

    public enum Role {
        FIREFIGHTER, PUBLIC, SUPERADMIN
    }
}