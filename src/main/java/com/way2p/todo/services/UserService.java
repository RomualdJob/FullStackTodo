package com.way2p.todo.services;

import com.way2p.todo.dto.RoleDTO;
import com.way2p.todo.entity.Role;
import com.way2p.todo.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Méthode pour récupérer un rôle par son nom
    public RoleDTO getRoleByName(String roleName) {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        // Crée un Set contenant uniquement les noms des utilisateurs associés au rôle
        Set<String> userNames = role.getUsers().stream()
                .map(user -> user.getName())  // On récupère le nom des utilisateurs associés
                .collect(Collectors.toSet());

        return new RoleDTO(role.getId(), role.getRoleName(), role.getUsers(),userNames);
    }

    // Méthode pour récupérer tous les rôles avec leurs utilisateurs
    public List<RoleDTO> getRolesWithUsers() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> {
            Set<String> userNames = role.getUsers().stream()
                    .map(user -> user.getName())
                    .collect(Collectors.toSet());
            return new RoleDTO(role.getId(), role.getRoleName(), userNames);
        }).collect(Collectors.toList());
    }
}
