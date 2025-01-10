package com.way2p.todo.repositories;

import com.way2p.todo.entity.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si nécessaire
}
