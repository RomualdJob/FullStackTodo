package com.way2p.todo.controllers;


import com.way2p.todo.dto.UserRoleDTO;
import com.way2p.todo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private com.way2p.todo.service.UserService roleService;

    // Endpoint pour récupérer un rôle par son nom


    // Endpoint pour récupérer tous les rôles avec leurs utilisateurs
    @GetMapping("/roles-users")
    public ResponseEntity<List<UserRoleDTO>> getRolesWithUsers() {
        List<UserRoleDTO> rolesDTO = roleService.getAllUsersWithRoles();
        return ResponseEntity.ok(rolesDTO);
    }
}
