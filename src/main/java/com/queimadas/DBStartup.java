package com.queimadas;

import com.queimadas.model.ERole;
import com.queimadas.model.Role;
import com.queimadas.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DBStartup implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public DBStartup(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (roleRepository.findAll().isEmpty()) {
            for (ERole value : ERole.values()) {
                roleRepository.save(new Role(value));
            }
        }
    }
}
