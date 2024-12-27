package com.way2p.todo.repositories;

import com.way2p.todo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Méthode pour trouver un rôle par son nom
    Optional<Role> findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);

    @Query("SELECT r FROM Role r JOIN FETCH r.users WHERE r.roleName = :roleName")
    List<Role> findByRoleNameWithUsers(@Param("roleName") String roleName);

}
