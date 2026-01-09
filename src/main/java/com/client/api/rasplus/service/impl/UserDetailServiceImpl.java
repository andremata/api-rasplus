package com.client.api.rasplus.service.impl;

import com.client.api.rasplus.exception.NotFoundException;
import com.client.api.rasplus.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var userDetailOpt = userDetailRepository.findByUsername(username);

        if(userDetailOpt.isPresent()) {
            return userDetailOpt.get();
        }
        throw new NotFoundException("Usuário não encontrado!");
    }
}
