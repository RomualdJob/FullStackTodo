package com.way2p.todo.services;

import com.way2p.todo.entity.Mission;
import com.way2p.todo.entity.User;
import com.way2p.todo.repositories.MissionRepository;
import com.way2p.todo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private UserRepository userRepository;

    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    // Méthode pour assigner une mission à un utilisateur par ID
    public Mission assignMissionToUser(Long userId, Mission mission) {
        // Chercher l'utilisateur par son ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur avec ID " + userId + " non trouvé"));

        // Assigner l'utilisateur à la mission
        mission.setUser(user);

        // Sauvegarder la mission
        return missionRepository.save(mission);
    }
}
