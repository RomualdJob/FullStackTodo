package com.way2p.todo.services;

import com.way2p.todo.dto.SignupRequest;
import com.way2p.todo.entity.Role;
import com.way2p.todo.entity.User;
import com.way2p.todo.repositories.RoleRepository;
import com.way2p.todo.repositories.UserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean createUser(SignupRequest signupRequest) {
        // Vérifier si l'utilisateur existe déjà par son email
        Optional<User> existingUserByEmail = userRepository.findByEmail(signupRequest.getEmail());
        if (existingUserByEmail.isPresent()) {
            return false;  // Utilisateur existe déjà par email
        }

        // Vérifier si l'utilisateur existe déjà par son nom
        Optional<User> existingUserByName = userRepository.findByName(signupRequest.getName());
        if (existingUserByName.isPresent()) {
            return false;  // Utilisateur existe déjà par nom
        }

        // Créer un nouvel utilisateur
        User user = new User();
        BeanUtils.copyProperties(signupRequest, user);

        // Hacher le mot de passe avant de l'enregistrer
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(hashPassword);

        // Récupérer les rôles valides pour cet utilisateur
        Set<Role> roles = getValidRoles(signupRequest.getRoleNames());

        // Assigner les rôles à l'utilisateur
        user.setRoles(roles);

        // Sauvegarder l'utilisateur
        userRepository.save(user);
        return true;  // Utilisateur créé avec succès
    }

    private Set<Role> getValidRoles(List<String> roleNames) {
        Set<Role> roles = new HashSet<>();

        // Si aucune liste de rôles n'est fournie, on assigne par défaut le rôle USER
        if (roleNames == null || roleNames.isEmpty()) {
            roleNames.add("USER");
        }

        for (String roleName : roleNames) {
            // Vérifier que le rôle est valide (USER ou ADMIN)
            if (!roleName.equals("USER") && !roleName.equals("ADMIN")) {
                throw new IllegalArgumentException("Role not valid. Only 'USER' and 'ADMIN' are allowed.");
            }

            // Chercher le rôle dans la base de données
            Role role = roleRepository.findByRoleName(roleName)
                    .orElseThrow(() -> new RuntimeException("Role " + roleName + " not found"));

            // Ajouter le rôle à l'ensemble
            roles.add(role);
        }

        return roles;
    }
}
