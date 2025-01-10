package com.way2p.todo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private double budget;
    private LocalDate datePublication;
    private LocalDate dateLimit;

    // Relation Many-to-One avec l'entité User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // La clé étrangère vers User
    private User user;

    // Constructeurs, getters et setters
    public Mission() {}

    public Mission(String titre, String description, double budget, LocalDate datePublication, LocalDate dateLimit, User user) {
        this.titre = titre;
        this.description = description;
        this.budget = budget;
        this.datePublication = datePublication;
        this.dateLimit = dateLimit;
        this.user = user;
    }

    // Getters et setters
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

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public LocalDate getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(LocalDate datePublication) {
        this.datePublication = datePublication;
    }

    public LocalDate getDateLimit() {
        return dateLimit;
    }

    public void setDateLimit(LocalDate dateLimit) {
        this.dateLimit = dateLimit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
