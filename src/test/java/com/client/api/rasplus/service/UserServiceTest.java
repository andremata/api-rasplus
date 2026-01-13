package com.client.api.rasplus.service;

import com.client.api.rasplus.dto.UserDTO;
import com.client.api.rasplus.exception.BadRequestException;
import com.client.api.rasplus.exception.NotFoundException;
import com.client.api.rasplus.model.jpa.User;
import com.client.api.rasplus.model.jpa.UserType;
import com.client.api.rasplus.repository.jpa.UserRepository;
import com.client.api.rasplus.repository.jpa.UserTypeRepository;
import com.client.api.rasplus.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private UserType userType;
    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void loadUser() {
        userType = new UserType(1L, "Administrador", "Administrador do Sistema");

        userDTO = new UserDTO();
        userDTO.setName("Fulano de Tal");
        userDTO.setEmail("filano@email.com");
        userDTO.setCpf("12345678999");
        userDTO.setDtSubscription(LocalDate.now());
        userDTO.setDtExpiration(LocalDate.now());
        userDTO.setUserTypeId(userType.getId());

        user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setCpf(userDTO.getCpf());
        user.setDtSubscription(userDTO.getDtSubscription());
        user.setDtExpiration(userDTO.getDtExpiration());
        user.setUserType(userType);
    }

    @Test
    void given_create_when_idIsNullAndUserIsFound_then_returnUserCreated() {
        when(userTypeRepository.findById(userType.getId())).thenReturn(Optional.of(userType));
        when(userRepository.save(user)).thenReturn(user);
        assertEquals(user, userService.create(userDTO));
        verify(userTypeRepository, times(1)).findById(userType.getId());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void given_create_when_idIsNotNull_then_throwBadRequestException() {
        userDTO.setId(1L);

        assertThrows(BadRequestException.class, () -> userService.create(userDTO));
        verify(userTypeRepository, times(0)).findById(any());
        verify(userRepository, times(0)).save(any());
    }

    @Test
    void given_create_when_idIsNullAndUserTypeIsNotNull_then_throwNotFoundException() {
        when(userTypeRepository.findById(userType.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.create(userDTO));
        verify(userTypeRepository, times(1)).findById(userType.getId());
        verify(userRepository, times(0)).save(any());
    }
}
