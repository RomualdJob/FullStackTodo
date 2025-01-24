package com.way2p.todo.controllers;


import com.way2p.todo.dto.UserRoleDTO;
import com.way2p.todo.entity.Role;
import com.way2p.todo.entity.User;
import com.way2p.todo.repositories.RoleRepository;
import com.way2p.todo.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

 //   @Autowired
    // Endpoint pour récupérer un rôle par son nom


    // Endpoint pour récupérer tous les rôles avec leurs utilisateurs
    @GetMapping("/roles-users")
    public ResponseEntity<List<UserRoleDTO>> getRolesWithUsers() {
        List<UserRoleDTO> rolesDTO = roleService.getAllUsersWithRoles(); // Appel du service
        return ResponseEntity.ok(rolesDTO);
    }

    @GetMapping("/all")
    public List<Role> getRoles() {
        return roleService.getRoles();  // Appeler le service pour récupérer les rôles
    }

}
