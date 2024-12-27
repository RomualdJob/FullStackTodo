package com.way2p.todo.dto;

public class SignupRequest {

    private String email;
    private String name;
    private String password;
    private String roleName; // Ajoutez ce champ pour spécifier le rôle

    public String getEmail() {
        return email;
    }


    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    public String getRoleName() {
        return roleName;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
