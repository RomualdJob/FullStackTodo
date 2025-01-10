package com.way2p.todo.controllers;

import com.way2p.todo.entity.Competence;
import com.way2p.todo.repositories.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/competences")
public class CompetenceController {

    @Autowired
    private CompetenceRepository competenceRepository;

    // Récupérer toutes les compétences
    @GetMapping
    public List<Competence> getAllCompetences() {
        return competenceRepository.findAll();
    }

    // Récupérer une compétence par ID
    @GetMapping("/{id}")
    public Optional<Competence> getCompetenceById(@PathVariable Long id) {
        return competenceRepository.findById(id);
    }

    // Ajouter une nouvelle compétence
    @PostMapping
    public Competence createCompetence(@RequestBody Competence competence) {
        return competenceRepository.save(competence);
    }

    // Mettre à jour une compétence existante
    @PutMapping("/{id}")
    public Competence updateCompetence(@PathVariable Long id, @RequestBody Competence competence) {
        competence.setId(id);
        return competenceRepository.save(competence);
    }

    // Supprimer une compétence
    @DeleteMapping("/{id}")
    public void deleteCompetence(@PathVariable Long id) {
        competenceRepository.deleteById(id);
    }
}
