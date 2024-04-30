package com.practica.controllers;

import com.practica.services.UserDetailsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/auth")
public class AuthenticationController {

    private UserDetailsServiceImpl userDetailsService;

    public ResponseEntity<AuthResponse> login(AuthLoginRequest userRequest) {
        
    }


}
