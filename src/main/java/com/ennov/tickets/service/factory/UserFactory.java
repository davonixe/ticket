package com.ennov.tickets.service.factory;

import org.springframework.http.ResponseEntity;

import com.ennov.tickets.controller.AuthController;
import com.ennov.tickets.payload.SignupRequest;


public class UserFactory {

    public ResponseEntity<?> createUserFactory(SignupRequest signUpRequest) {
        return new AuthController().registerUser(signUpRequest);
    }
}
