package com.way2p.todo.services;

import com.way2p.todo.dto.SignupRequest;

public interface AuthService {
    boolean createUser(SignupRequest signupRequest);
}
