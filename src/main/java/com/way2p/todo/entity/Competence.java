package com.way2p.todo.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Competence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle; // Le libellé de la compétence

    // Relation Many-to-Many avec l'entité User
    @ManyToMany(mappedBy = "competences", fetch = FetchType.LAZY)
    private Set<User> users = new HashSet<>();

    // Constructeurs, getters et setters
    public Competence() {}

    public Competence(String libelle) {
        this.libelle = libelle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
