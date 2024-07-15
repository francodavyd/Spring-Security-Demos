package com.francodavyd.controller;

import com.francodavyd.dto.AuthLoginRequestDTO;
import com.francodavyd.dto.AuthResponseDTO;
import com.francodavyd.service.implementations.UserDetailsServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO userRequest) {
        return new ResponseEntity<>(userDetailsService.loginUser(userRequest), HttpStatus.OK);
    }
}
