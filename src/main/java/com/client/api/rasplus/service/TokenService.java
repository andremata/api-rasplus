package com.client.api.rasplus.service;

import org.springframework.security.core.Authentication;

public interface TokenService {

    String getToken(Authentication authentication);

    Boolean isValue(String token);

    Long getUserId(String token);
}
