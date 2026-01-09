package com.client.api.rasplus.service.impl;

import com.client.api.rasplus.dto.LoginDTO;
import com.client.api.rasplus.dto.TokenDTO;
import com.client.api.rasplus.service.AuthenticationService;
import com.client.api.rasplus.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public TokenDTO auth(LoginDTO dto) {
        UsernamePasswordAuthenticationToken userToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassworld());

        Authentication authentication = authenticationManager.authenticate(userToken);

        String token = tokenService.getToken(authentication);

        return TokenDTO.builder().token(token).type("Bearer").build();
    }
}
