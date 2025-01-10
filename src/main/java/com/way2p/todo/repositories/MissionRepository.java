package com.way2p.todo.repositories;

import com.way2p.todo.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    // Tu peux ajouter des méthodes personnalisées ici si nécessaire, par exemple pour rechercher par titre ou par date
}
