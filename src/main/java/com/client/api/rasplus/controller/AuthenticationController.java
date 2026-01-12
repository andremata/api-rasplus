package com.client.api.rasplus.controller;

import com.client.api.rasplus.dto.LoginDTO;
import com.client.api.rasplus.dto.TokenDTO;
import com.client.api.rasplus.model.redis.UserRecoveryCode;
import com.client.api.rasplus.service.AuthenticationService;
import com.client.api.rasplus.service.UserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<TokenDTO> auth(@RequestBody LoginDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.auth(dto));
    }

    @PostMapping("/send-recovery-code")
    public ResponseEntity<?> sendRecoveryCode(@Valid @RequestBody UserRecoveryCode userRecoveryCode) {
        userDetailsService.sendRecoveryCode(userRecoveryCode.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/recovery-code")
    public ResponseEntity<?> recoveryCodeIsValid(@RequestParam("recoveryCode") String recoveryCode,
                                                 @RequestParam("email") String email) {

        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(userDetailsService.recoveryCodeIsValid(recoveryCode, email));
    }
}
