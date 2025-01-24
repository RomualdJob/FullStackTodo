package com.way2p.todo.controllers;

import com.way2p.todo.dto.SignupRequest;
import com.way2p.todo.dto.UserRoleDTO;
import com.way2p.todo.entity.User;
import com.way2p.todo.repositories.UserRepository;
import com.way2p.todo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Récupérer tous les utilisateurs avec leurs rôles
    @GetMapping("/all")
    public ResponseEntity<List<UserRoleDTO>> getAllUsersWithRoles() {
        List<UserRoleDTO> users = userRepository.findAll().stream()
                .map(user -> new UserRoleDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRoles().stream()
                                .map(role -> role.getRoleName())
                                .collect(Collectors.toSet())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    // Récupérer un utilisateur par ID avec ses rôles
    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDTO> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Si l'utilisateur n'existe pas
        }
        User user = userOptional.get();
        UserRoleDTO userRoleDTO = new UserRoleDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(role -> role.getRoleName())
                        .collect(Collectors.toSet())
        );
        return ResponseEntity.ok(userRoleDTO);
    }

    // Créer un nouvel utilisateur avec rôle
    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody SignupRequest signupRequest) {
        // Vérification si un utilisateur existe déjà avec le même email
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Un utilisateur avec ce email existe déjà.");
        }

        // Création de l'utilisateur
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword())); // Encodage du mot de passe

        // Recherche et ajout des rôles
        user.setRoles(roleService.getRolesByNames(signupRequest.getRoleNames()));

        // Sauvegarder l'utilisateur avec ses rôles
        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur créé avec succès !");
    }


    // Mettre à jour un utilisateur existant
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody SignupRequest signupRequest) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé.");
        }

        User user = userOptional.get();

        // Vérification si l'email est déjà utilisé par un autre utilisateur
        if (!user.getEmail().equals(signupRequest.getEmail()) && userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Un utilisateur avec ce email existe déjà.");
        }

        // Mise à jour des informations de l'utilisateur
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());

        // Mise à jour du mot de passe si fourni
        if (signupRequest.getPassword() != null && !signupRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword())); // Encodage du mot de passe
        }

        // Mise à jour des rôles
        user.setRoles(roleService.getRolesByNames(signupRequest.getRoleNames()));

        // Sauvegarde de l'utilisateur mis à jour
        userRepository.save(user);

        return ResponseEntity.ok("Utilisateur mis à jour avec succès !");
    }



    // Supprimer un utilisateur
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Si l'utilisateur n'existe pas
        }

        userRepository.deleteById(id);  // Supprimer l'utilisateur
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Réponse 204 No Content pour succès
    }

    @PostMapping("/remove-role/{userId}/{roleName}")
    public ResponseEntity<String> removeRoleFromUser(@PathVariable Long userId, @PathVariable String roleName) {
        try {
            roleService.removeRoleFromUser(userId, roleName); // Appel du service Role pour supprimer un rôle
            return ResponseEntity.ok("Rôle supprimé avec succès !");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
    }
}
