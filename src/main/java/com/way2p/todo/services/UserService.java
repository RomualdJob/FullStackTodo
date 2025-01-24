package com.way2p.todo.service;

import com.way2p.todo.entity.User;
import com.way2p.todo.dto.UserRoleDTO;
import com.way2p.todo.repositories.UserRepository;
import com.way2p.todo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }



    // Récupérer tous les utilisateurs avec leurs rôles sous forme de UserRoleDTO
    public List<UserRoleDTO> getAllUsersWithRoles() {
        return userRepository.findAll().stream()
                .map(user -> new UserRoleDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(role -> role.getRoleName()) // Récupérer le nom des rôles
                                .collect(Collectors.toSet())))
                .collect(Collectors.toList());
    }

    // Trouver un utilisateur par son email et récupérer ses rôles sous forme de UserRoleDTO
    public UserRoleDTO getUserWithRolesByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return new UserRoleDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> role.getRoleName()) // Récupérer le nom du rôle
                        .collect(Collectors.toSet()));
    }

    // Ajouter un rôle à un utilisateur
    public void addRoleToUser(Long userId, String roleName) {
        roleService.addRoleToUser(userId, roleName); // Appel à RoleService pour ajouter un rôle
    }

    // Supprimer un rôle d'un utilisateur
    public void removeRoleFromUser(Long userId, String roleName) {
        roleService.removeRoleFromUser(userId, roleName); // Appel à RoleService pour supprimer un rôle
    }
}
