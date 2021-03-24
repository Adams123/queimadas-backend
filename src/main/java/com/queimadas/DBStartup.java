package com.queimadas;

import com.queimadas.model.ERole;
import com.queimadas.model.Role;
import com.queimadas.repository.RoleRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DBStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final RoleRepository roleRepository;

    public DBStartup(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (roleRepository.findAll().isEmpty()) {
            for (ERole value : ERole.values()) {
                roleRepository.save(new Role(value));
            }
        }
    }
}
