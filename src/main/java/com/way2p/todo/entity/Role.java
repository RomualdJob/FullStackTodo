package com.way2p.todo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nom du rôle (par exemple, "USER", "ADMIN")
    private String roleName;

    // Constructeur sans argument requis par JPA
    public Role() {}

    // Constructeur pour initialiser rapidement un rôle
    public Role(String roleName) {
        this.roleName = roleName;
    }

    // Getter et setter pour le rôle
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Relation Many-to-Many avec l'entité User
    @ManyToMany(mappedBy = "roles") // La relation est gérée par l'entité User
    private Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
