package com.way2p.todo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int note; // La note de l'évaluation, par exemple sur 5 ou 10
    private String commentaire; // Le commentaire laissé par le client
    private LocalDate dateEvaluation; // La date de l'évaluation

    // Relation Many-to-One avec l'entité User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // La clé étrangère vers User
    private User user;

    // Constructeurs, getters et setters
    public Evaluation() {}

    public Evaluation(int note, String commentaire, LocalDate dateEvaluation, User user) {
        this.note = note;
        this.commentaire = commentaire;
        this.dateEvaluation = dateEvaluation;
        this.user = user;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public LocalDate getDateEvaluation() {
        return dateEvaluation;
    }

    public void setDateEvaluation(LocalDate dateEvaluation) {
        this.dateEvaluation = dateEvaluation;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
