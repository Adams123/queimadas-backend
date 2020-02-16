package com.ufscar.queimadas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users") //cannot be user, it's a postgresql reserved word
public class User {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String password;
    private UserRoles userRole;

    public User(String name, String password) {
        this.id = UUID.randomUUID();
        this.userRole = UserRoles.PUBLIC;
        this.name = name;
        this.password = password;
    }
}
