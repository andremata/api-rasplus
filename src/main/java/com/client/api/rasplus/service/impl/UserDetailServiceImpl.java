package com.client.api.rasplus.service.impl;

import com.client.api.rasplus.exception.NotFoundException;
import com.client.api.rasplus.integration.MailIntegration;
import com.client.api.rasplus.model.jpa.UserCredentials;
import com.client.api.rasplus.model.redis.UserRecoveryCode;
import com.client.api.rasplus.repository.jpa.UserDetailRepository;
import com.client.api.rasplus.repository.redis.UserRecoveryCodeRepository;
import com.client.api.rasplus.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Value("${ws.rasplus.redis.recoverycode.timeout}")
    private Long timeoutMinute;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Autowired
    private MailIntegration mailIntegration;

    @Override
    public UserCredentials loadUserByUsernameAndPass(String username, String password) {
        var userDetailOpt = userDetailRepository.findByUsername(username);

        if(userDetailOpt.isPresent()) {
            return userDetailOpt.get();
        }
        throw new NotFoundException("Usuário não encontrado!");
    }

    @Override
    public void sendRecoveryCode(String email) {
        String code = String.format("%04d", new Random().nextInt(10000));

        UserRecoveryCode userRecoveryCode;

        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if(userRecoveryCodeOpt.isEmpty()) {
            var userOpt = userDetailRepository.findByUsername(email);

            if(userOpt.isEmpty()) {
                throw new NotFoundException("Usuário não encontrado!");
            }

            userRecoveryCode = new UserRecoveryCode();
            userRecoveryCode.setEmail(email);
        } else {
            userRecoveryCode = userRecoveryCodeOpt.get();
        }

        userRecoveryCode.setCode(code);
        userRecoveryCode.setCreationDate(LocalDateTime.now());

        userRecoveryCodeRepository.save(userRecoveryCode);
        mailIntegration.send(email, "Seu código de recuperação chegou!", "Código de recuperação de conta");
    }

    @Override
    public boolean recoveryCodeIsValid(String recoveryCode, String email) {
        var userRecoveryCodeOpt = userRecoveryCodeRepository.findByEmail(email);

        if(userRecoveryCodeOpt.isEmpty()) {
            throw new NotFoundException("Usuário não encontrado!");
        }

        UserRecoveryCode userRecoveryCode = userRecoveryCodeOpt.get();

        if(!recoveryCode.equals(userRecoveryCode.getCode())) {
            throw new NotFoundException("Código de verificação inválido!");
        }

        LocalDateTime timeout = userRecoveryCode.getCreationDate().plusMinutes(timeoutMinute);
        LocalDateTime now = LocalDateTime.now();

        if(now.isAfter(timeout)) {
            throw new NotFoundException("Código de verificação expirado!");
        }

        return true;
    }
}
