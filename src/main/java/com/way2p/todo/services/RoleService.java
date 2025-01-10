package com.way2p.todo.service;

import com.way2p.todo.entity.Role;
import com.way2p.todo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Récupérer un rôle par son nom
    public Role getRoleByName(String roleName) {
        Optional<Role> role = roleRepository.findByRoleName(roleName);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new RuntimeException("Rôle non trouvé");
        }
    }
}
