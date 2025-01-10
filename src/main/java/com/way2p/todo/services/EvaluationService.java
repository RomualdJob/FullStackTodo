package com.way2p.todo.services;

import com.way2p.todo.entity.User;
import com.way2p.todo.entity.Evaluation;
import com.way2p.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EvaluationService {

    @Autowired
    private UserRepository userRepository;

    // Méthode pour récupérer les évaluations d'un utilisateur par son ID
    public Set<Evaluation> getEvaluationsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur avec ID " + userId + " non trouvé"));

        // Récupérer toutes les évaluations de l'utilisateur
        return user.getEvaluations(); // Cette méthode accède directement à la collection d'évaluations de l'utilisateur
    }
}
