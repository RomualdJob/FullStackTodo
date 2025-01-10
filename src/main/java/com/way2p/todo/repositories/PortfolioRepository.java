package com.way2p.todo.repositories;

import com.way2p.todo.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    // Tu peux ajouter des méthodes personnalisées si nécessaire
}