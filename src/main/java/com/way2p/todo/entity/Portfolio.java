package com.way2p.todo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private LocalDate dateCreation;
    private String imageUrl;

    // Relation One-to-One avec l'entité User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // La clé étrangère vers User
    private User user;

    // Constructeurs, getters et setters
    public Portfolio() {
    }

    public Portfolio(String titre, String description, LocalDate dateCreation, String imageUrl, User user) {
        this.titre = titre;
        this.description = description;
        this.dateCreation = dateCreation;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
