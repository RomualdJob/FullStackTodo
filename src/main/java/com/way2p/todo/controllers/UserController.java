package com.way2p.todo.controllers;

import com.way2p.todo.dto.SignupRequest;
import com.way2p.todo.dto.UserRoleDTO;
import com.way2p.todo.entity.User;
import com.way2p.todo.services.AuthServiceImpl;
import com.way2p.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthServiceImpl authService;

    @Autowired
    private com.way2p.todo.service.UserService userService;


    // Récupérer tous les utilisateurs
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userRepository.findById(id);
    }

    // Récupérer un utilisateur par nom
    @GetMapping("/name/{name}")
    public Optional<User> getUserByName(@PathVariable String name) {
        return userRepository.findByName(name);
    }

    // Créer un nouvel utilisateur
    @PostMapping("/signup")
    public String createUser(@RequestBody SignupRequest signupRequest) {
        boolean userCreated = authService.createUser(signupRequest);
        if (userCreated) {
            return "Utilisateur créé avec succès!";
        } else {
            return "Un utilisateur avec ce nom ou email existe déjà.";
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        // Recherche l'utilisateur par ID
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Si l'utilisateur n'existe pas
        }

        User existingUser = userOptional.get();

        // Mise à jour des champs de l'utilisateur
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());  // N'oublie pas de hacher le mot de passe si nécessaire

        // Sauvegarder les modifications
        userRepository.save(existingUser);

        return ResponseEntity.ok(existingUser);  // Retourne l'utilisateur mis à jour
    }

    @GetMapping("/users-roles")
    public List<UserRoleDTO> getUsersWithRoles() {
        return userService.getAllUsersWithRoles();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        // Recherche l'utilisateur par ID
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Si l'utilisateur n'existe pas
        }

        userRepository.deleteById(id);  // Supprimer l'utilisateur
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();  // Réponse 204 No Content pour succès
    }


}
