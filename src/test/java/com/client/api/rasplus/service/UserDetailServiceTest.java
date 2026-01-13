package com.client.api.rasplus.service;

import com.client.api.rasplus.exception.BadRequestException;
import com.client.api.rasplus.exception.NotFoundException;
import com.client.api.rasplus.integration.MailIntegration;
import com.client.api.rasplus.model.jpa.UserCredentials;
import com.client.api.rasplus.model.jpa.UserType;
import com.client.api.rasplus.model.redis.UserRecoveryCode;
import com.client.api.rasplus.repository.jpa.UserDetailRepository;
import com.client.api.rasplus.repository.redis.UserRecoveryCodeRepository;
import com.client.api.rasplus.service.impl.UserDetailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserDetailServiceTest {

    private static final String USERNAME_ALUNO = "andre@email.com";
    private static final String PASSWORD_ALUNO = "senha123";
    private static final String RECOVERY_CODE = "4065";

    @Mock
    private UserRecoveryCodeRepository userRecoveryCodeRepository;

    @Mock
    private UserDetailRepository userDetailRepository;

    @Mock
    private MailIntegration mailIntegration;

    @InjectMocks
    private UserDetailServiceImpl userDetailService;

    @Test
    void given_loadUserByUsernameAndPass_when_thereIsUsername_then_returnUserCredentials() {
        UserCredentials userCredentials = getUserCredentials();

        when(userDetailRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(userCredentials));
        assertEquals(userCredentials, userDetailService.loadUserByUsernameAndPass(USERNAME_ALUNO, PASSWORD_ALUNO));
        verify(userDetailRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }

    @Test
    void given_loadUserByUsernameAndPass_when_thereIsNoUsername_then_throwNotFoundException() {
        when(userDetailRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.empty());

        assertEquals("Usuário não encontrado!",
                assertThrows(NotFoundException.class,
                        () -> userDetailService.loadUserByUsernameAndPass(USERNAME_ALUNO, PASSWORD_ALUNO)
                ).getLocalizedMessage());

        verify(userDetailRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }

    @Test
    void given_loadUserByUsernameAndPass_when_passIsNotCorrect_then_throwBadRequestException() {
        UserCredentials userCredentials = getUserCredentials();

        when(userDetailRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(userCredentials));

        assertEquals("Usuário ou senha inválido!",
                assertThrows(BadRequestException.class,
                        () -> userDetailService.loadUserByUsernameAndPass(USERNAME_ALUNO, "pass")
                ).getLocalizedMessage());

        verify(userDetailRepository, times(1)).findByUsername(USERNAME_ALUNO);
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsFound_then_updateUserAndSendEmail() {
        UserRecoveryCode userRecoveryCode = getUserRecoveryCode();

        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.of(userRecoveryCode));

        userDetailService.sendRecoveryCode(USERNAME_ALUNO);

        verify(userRecoveryCodeRepository, times(1)).save(any());
        verify(mailIntegration, times(1)).send(any(), any(), any());
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsNotFound_then_SaveUserAndSendEmail() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.empty());
        when(userDetailRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.of(getUserCredentials()));

        userDetailService.sendRecoveryCode(USERNAME_ALUNO);

        verify(userRecoveryCodeRepository, times(1)).save(any());
        verify(mailIntegration, times(1)).send(any(), any(), any());
    }

    @Test
    void given_sendRecoveryCode_when_userRecoveryCodeIsNotFoundAndUserDetailsIsNotFound_then_throwNotFoundException() {
        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.empty());
        when(userDetailRepository.findByUsername(USERNAME_ALUNO)).thenReturn(Optional.empty());

        try {
            userDetailService.sendRecoveryCode(USERNAME_ALUNO);
        } catch (Exception e) {
            assertEquals(NotFoundException.class, e.getClass());
            assertEquals("Usuário não encontrado!", e.getMessage());
        }

        verify(userRecoveryCodeRepository, times(0)).save(any());
        verify(mailIntegration, times(0)).send(any(), any(), any());
    }

    @Test
    void given_recoveryCodeIsValid_when_userIsFound_then_returnTrue() {
        ReflectionTestUtils.setField(userDetailService, "timeoutMinute", "5");

        when(userRecoveryCodeRepository.findByEmail(USERNAME_ALUNO)).thenReturn(Optional.of(getUserRecoveryCode()));
        assertTrue(userDetailService.recoveryCodeIsValid(RECOVERY_CODE, USERNAME_ALUNO));
        verify(userRecoveryCodeRepository, times(1)).findByEmail(USERNAME_ALUNO);
    }

    private static UserRecoveryCode getUserRecoveryCode() {
        return new UserRecoveryCode(UUID.randomUUID().toString(), USERNAME_ALUNO, RECOVERY_CODE, LocalDateTime.now());
    }

    private UserCredentials getUserCredentials() {
        UserType userType = new UserType(1L, "aluno", "aluno plataforma");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return new UserCredentials(1L, USERNAME_ALUNO, encoder.encode(PASSWORD_ALUNO), userType);
    }
}
