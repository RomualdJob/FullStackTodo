package com.way2p.todo.services;

import com.way2p.todo.dto.UserRoleDTO;
import com.way2p.todo.entity.Role;
import com.way2p.todo.entity.User;
import com.way2p.todo.repositories.RoleRepository;
import com.way2p.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    // Recherche d'un rôle par son nom
    public Optional<Role> findRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    // Ajouter un rôle à un utilisateur
    public void addRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    // Supprimer un rôle d'un utilisateur
    public void removeRoleFromUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    // Récupérer tous les rôles
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    // Récupérer un rôle par son nom (version plus simple)
    public Role getRoleByName(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new RuntimeException("Rôle non trouvé"));
    }

    // Récupérer plusieurs rôles par leurs noms
    public Set<Role> getRolesByNames(List<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : roleNames) {
            // Récupérer le rôle par son nom, ou lancer une exception si non trouvé
            Role role = roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Le rôle " + roleName + " n'existe pas."));
            roles.add(role);  // Ajouter le rôle trouvé à l'ensemble des rôles
        }
        return roles;
    }


    // Récupérer tous les utilisateurs avec leurs rôles
    public List<UserRoleDTO> getAllUsersWithRoles() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserRoleDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(role -> role.getRoleName())  // Récupérer le nom des rôles
                                .collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }
}
