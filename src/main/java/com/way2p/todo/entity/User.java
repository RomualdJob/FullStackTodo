package com.way2p.todo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    // Relation Many-to-Many avec l'entité Role
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles", // Nom de la table de jointure
            joinColumns = @JoinColumn(name = "user_id"),  // Clé étrangère pour User
            inverseJoinColumns = @JoinColumn(name = "role_id") // Clé étrangère pour Role
    )
    private Set<Role> roles = new HashSet<>();

    // Getters et setters
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
