package com.way2p.todo.repositories;

import com.way2p.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Recherche un utilisateur par email
    Optional<User> findByEmail(String email);

    // Recherche d'un utilisateur par son email et récupération de ses rôles associés
    @Query("SELECT u FROM User u JOIN FETCH u.roles WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);

    // Vérifie si un utilisateur existe déjà avec cet email
    boolean existsByEmail(String email);
}
