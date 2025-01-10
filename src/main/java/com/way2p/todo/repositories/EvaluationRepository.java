package com.way2p.todo.repositories;

import com.way2p.todo.entity.Evaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    // Trouver toutes les évaluations liées à un freelancer par son ID
    List<Evaluation> findByUserId(Long userId);
}