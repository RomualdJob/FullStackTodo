package com.way2p.todo.controllers;

import com.way2p.todo.entity.Mission;
import com.way2p.todo.entity.User;
import com.way2p.todo.repositories.MissionRepository;
//import com.way2p.todo.services.UserNotFoundException;
import com.way2p.todo.services.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/missions")
public class MissionController {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private MissionService missionService; // Injection du service

    // Récupérer toutes les missions
    @GetMapping
    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    // Récupérer une mission par ID
    @GetMapping("/{id}")
    public Optional<Mission> getMissionById(@PathVariable Long id) {
        return missionRepository.findById(id);
    }

    // Ajouter une nouvelle mission
    @PostMapping
    public Mission createMission(@RequestBody Mission mission) {
        return missionRepository.save(mission);
    }

    // Mettre à jour une mission existante
    @PutMapping("/{id}")
    public Mission updateMission(@PathVariable Long id, @RequestBody Mission mission) {
        mission.setId(id);
        return missionRepository.save(mission);
    }

    // Supprimer une mission
    @DeleteMapping("/{id}")
    public void deleteMission(@PathVariable Long id) {
        missionRepository.deleteById(id);
    }

    // Assigner une mission à un utilisateur
    @PostMapping("/assign/{userId}")
    public ResponseEntity<Mission> assignMissionToUser(@PathVariable Long userId, @RequestBody Mission mission) {
        try {
            // Appeler le service pour assigner la mission à l'utilisateur
            Mission updatedMission = missionService.assignMissionToUser(userId, mission);

            // Si tout se passe bien, retourner la mission mise à jour avec un code HTTP 200 OK
            return new ResponseEntity<>(updatedMission, HttpStatus.OK);
        } catch (MissionService.UserNotFoundException e) {
            // Si l'utilisateur n'est pas trouvé, retourner une erreur HTTP 404 NOT FOUND
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
