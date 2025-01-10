package com.way2p.todo.repositories;

import com.way2p.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Recherche un utilisateur par son nom
    Optional<User> findByName(String name);

    // Recherche un utilisateur par son email
    Optional<User> findByEmail(String email);
}

