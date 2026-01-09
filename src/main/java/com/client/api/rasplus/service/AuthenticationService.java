package com.client.api.rasplus.service;

import com.client.api.rasplus.dto.LoginDTO;
import com.client.api.rasplus.dto.TokenDTO;

public interface AuthenticationService {

    TokenDTO auth(LoginDTO dto);
}
