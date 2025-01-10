package com.way2p.todo.service;

import com.way2p.todo.entity.User;
import com.way2p.todo.dto.UserRoleDTO;
import com.way2p.todo.entity.Role;
import com.way2p.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserRoleDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(Role::getRoleName) // On récupère le nom du rôle
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
                        .map(Role::getRoleName) // On récupère le nom du rôle
                        .collect(Collectors.toSet()));
    }

    // Ajouter un rôle à un utilisateur
    public void addRoleToUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Role role = roleService.getRoleByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    // Supprimer un rôle d'un utilisateur
    public void removeRoleFromUser(Long userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Role role = roleService.getRoleByName(roleName);
        user.getRoles().remove(role);
        userRepository.save(user);
    }
}
