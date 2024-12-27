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
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return false;  // Utilisateur existe déjà
        }

        // Créer un nouvel utilisateur
        User user = new User();
        BeanUtils.copyProperties(signupRequest, user);

        // Hacher le mot de passe avant de l'enregistrer
        String hashPassword = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(hashPassword);

        // Récupérer le rôle fourni ou utiliser un rôle par défaut (USER)
        Role userRole;
        if (signupRequest.getRoleName() != null && !signupRequest.getRoleName().isEmpty()) {
            // Si un rôle est fourni, on cherche ce rôle dans la base de données
            userRole = roleRepository.findByRoleName(signupRequest.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role " + signupRequest.getRoleName() + " not found"));
        } else {
            // Si aucun rôle n'est fourni, on assigne le rôle par défaut USER
            userRole = roleRepository.findByRoleName("USER")
                    .orElseThrow(() -> new RuntimeException("Default role USER not found"));
        }

        // Assigner le rôle à l'utilisateur
        user.getRoles().add(userRole);

        // Sauvegarder l'utilisateur
        userRepository.save(user);
        return true;  // Utilisateur créé avec succès
    }
}
