package com.way2p.todo.controllers;

import com.way2p.todo.entity.Mission;
import com.way2p.todo.entity.User;
import com.way2p.todo.repositories.MissionRepository;
import com.way2p.todo.repositories.UserRepository;  // Ajouter le repository de User
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
    private UserRepository userRepository; // Ajouter le repository de User

    @Autowired
    private MissionService missionService; // Injection du service

    // Récupérer toutes les missions
    @GetMapping
    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    // Récupérer une mission par ID
    @GetMapping("/{id}")
    public ResponseEntity<Mission> getMissionById(@PathVariable Long id) {
        // Recherche de la mission avec l'id
        Optional<Mission> missionOptional = missionRepository.findById(id);

        if (missionOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Mission non trouvée
        }

        Mission mission = missionOptional.get();

        // Si l'utilisateur est null, la mission ne contient pas d'utilisateur (ne sera pas serialisé si "user" est null)
        if (mission.getUser() != null) {
            // Cela va automatiquement sérialiser l'utilisateur si la relation est correctement configurée
            return new ResponseEntity<>(mission, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si pas d'utilisateur associé
        }
    }


    // Scénario 1 : Créer une mission avec un utilisateur associé lors de la création
    @PostMapping("/mission-user")
    public ResponseEntity<Mission> createMissionWithUser(@RequestBody Mission mission) {
        if (mission.getUser() == null || mission.getUser().getId() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // L'utilisateur doit être spécifié
        }

        // Vérifier si l'utilisateur existe
        Optional<User> userOptional = userRepository.findById(mission.getUser().getId());
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // L'utilisateur n'existe pas
        }

        User user = userOptional.get();
        mission.setUser(user); // Associer l'utilisateur à la mission
        Mission createdMission = missionRepository.save(mission);
        return new ResponseEntity<>(createdMission, HttpStatus.CREATED);
    }

    // Scénario 2 : Créer une mission sans utilisateur et associer un utilisateur après
    @PostMapping("/add-mission")
    public ResponseEntity<Mission> createMissionWithoutUser(@RequestBody Mission mission) {
        // Créer la mission sans spécifier d'utilisateur
        Mission createdMission = missionRepository.save(mission);
        return new ResponseEntity<>(createdMission, HttpStatus.CREATED);
    }

    // Scénario 3 : Assigner un utilisateur à une mission après la création
    @PutMapping("/assign/{missionId}/{userId}")
    public ResponseEntity<Mission> assignMissionToUser(@PathVariable Long missionId, @PathVariable Long userId) {
        // Vérifier si la mission existe
        Optional<Mission> missionOptional = missionRepository.findById(missionId);
        if (missionOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Mission non trouvée
        }

        // Vérifier si l'utilisateur existe
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Utilisateur non trouvé
        }

        // Associer l'utilisateur à la mission
        Mission mission = missionOptional.get();
        User user = userOptional.get();
        mission.setUser(user);
        missionRepository.save(mission);

        return new ResponseEntity<>(mission, HttpStatus.OK);  // Mission mise à jour
    }

    // Mettre à jour une mission existante
    @PutMapping("/{id}")
    public ResponseEntity<Mission> updateMission(@PathVariable Long id, @RequestBody Mission mission) {
        // Vérifier si la mission existe
        Optional<Mission> existingMission = missionRepository.findById(id);
        if (existingMission.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Mission non trouvée
        }
        mission.setId(id); // Mettre à jour l'ID
        missionRepository.save(mission);
        return new ResponseEntity<>(mission, HttpStatus.OK);
    }

    // Supprimer une mission
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        Optional<Mission> mission = missionRepository.findById(id);
        if (mission.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Mission non trouvée
        }
        missionRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Supprimé avec succès
    }
}
