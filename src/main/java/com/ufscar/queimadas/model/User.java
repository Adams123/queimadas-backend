package com.ufscar.queimadas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import static javax.persistence.CascadeType.REFRESH;
import static javax.persistence.CascadeType.REMOVE;
import static javax.persistence.FetchType.EAGER;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users") //cannot be user, it's a postgresql reserved word
public class User extends BaseModel implements UserDetails {

    @Column(unique = true)
    private String name;
    private String password;
    @ManyToMany(fetch = EAGER)
    @EqualsAndHashCode.Exclude
    private Set<Roles> roles = Collections.singleton(new Roles());

    @OneToOne(fetch = EAGER, mappedBy = "user", cascade = {REMOVE, REFRESH})
    private UserBearerToken userBearerToken;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return String.format("User %s: %s", getId(), getName());
    }
}
