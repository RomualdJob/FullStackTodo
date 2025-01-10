package com.way2p.todo.dto;

import java.util.HashSet;
import java.util.Set;

public class RoleDTO {

    private Long id;
    private String roleName;
    private String email;
    private Set<String> userNames = new HashSet<>(); // Contiendra les noms des utilisateurs associés au rôle


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Constructeur
    public RoleDTO(Long id, String roleName, String email,Set<String> userNames) {
        this.id = id;
        this.roleName = roleName;
        this.email=email;
        this.userNames = userNames;
    }

    // Getters et setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<String> getUserNames() {
        return userNames;
    }

    public void setUserNames(Set<String> userNames) {
        this.userNames = userNames;
    }
}
