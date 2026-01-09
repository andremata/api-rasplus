package com.client.api.rasplus.controller;

import com.client.api.rasplus.dto.LoginDTO;
import com.client.api.rasplus.dto.TokenDTO;
import com.client.api.rasplus.service.AuthenticationService;
import com.client.api.rasplus.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<TokenDTO> auth(@RequestBody LoginDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.auth(dto));
    }
}
