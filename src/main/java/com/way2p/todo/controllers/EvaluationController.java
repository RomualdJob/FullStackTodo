package com.way2p.todo.controllers;

import com.way2p.todo.entity.Evaluation;
import com.way2p.todo.services.EvaluationService;
import com.way2p.todo.repositories.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationService evaluationService; // Ajout de l'annotation @Autowired ici pour l'injection de dépendance

    // Récupérer toutes les évaluations
    @GetMapping
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

    // Récupérer une évaluation par ID
    @GetMapping("/{id}")
    public Optional<Evaluation> getEvaluationById(@PathVariable Long id) {
        return evaluationRepository.findById(id);
    }

    // Ajouter une nouvelle évaluation
    @PostMapping
    public Evaluation createEvaluation(@RequestBody Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    // Mettre à jour une évaluation existante
    @PutMapping("/{id}")
    public Evaluation updateEvaluation(@PathVariable Long id, @RequestBody Evaluation evaluation) {
        evaluation.setId(id);
        return evaluationRepository.save(evaluation);
    }

    // Supprimer une évaluation
    @DeleteMapping("/{id}")
    public void deleteEvaluation(@PathVariable Long id) {
        evaluationRepository.deleteById(id);
    }

    // Récupérer les évaluations d'un utilisateur par son ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Set<Evaluation>> getEvaluationsByUserId(@PathVariable Long userId) {
        Set<Evaluation> evaluations = evaluationService.getEvaluationsByUserId(userId);
        return ResponseEntity.ok(evaluations);
    }
}
