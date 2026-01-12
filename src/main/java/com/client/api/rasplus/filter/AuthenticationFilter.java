package com.client.api.rasplus.filter;

import com.client.api.rasplus.exception.NotFoundException;
import com.client.api.rasplus.model.jpa.UserCredentials;
import com.client.api.rasplus.repository.jpa.UserDetailRepository;
import com.client.api.rasplus.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Objects;

public class AuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getBearerToken(request);

        if(tokenService.isValue(token)) {
            authByToken(token);
        }

        filterChain.doFilter(request, response);
    }

    private void authByToken(String token) {
        Long userId = tokenService.getUserId(token);

        var userOpt = userDetailRepository.findById(userId);

        if(userOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado!");
        }

        UserCredentials userCredentials = userOpt.get();

        UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(userCredentials,
                null, userCredentials.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(userAuth);
    }

    private static String getBearerToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if(Objects.isNull(token) || !token.startsWith("Bearer")) {
            return null;
        }

        return token.substring(7, token.length());
    }
}
