package com.way2p.todo.data;

import com.way2p.todo.entity.Role;
import com.way2p.todo.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class RoleInitializationService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleInitializationService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void initRoles() {
        // Créer les rôles si non existants
        if (!roleRepository.existsByRoleName("USER")) {
            Role userRole = new Role();
            userRole.setRoleName("USER");
            roleRepository.save(userRole);
        }

        if (!roleRepository.existsByRoleName("ADMIN")) {
            Role adminRole = new Role();
            adminRole.setRoleName("ADMIN");
            roleRepository.save(adminRole);
        }
    }
}