package com.client.api.rasplus.service;

import com.client.api.rasplus.model.jpa.UserType;
import com.client.api.rasplus.repository.jpa.UserTypeRepository;
import com.client.api.rasplus.service.impl.UserTypeServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserTypeServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserTypeServiceImpl userTypeService;

    @Test
    void given_findAll_when_thereAreDatasDataBase_then_returnAllDatas() {
        Mockito.when(userTypeRepository.findAll()).thenReturn(createUserTypes());
        var result = userTypeService.findAll();

        Assertions.assertThat(result).hasSize(2).isNotEmpty();
    }

    List<UserType> createUserTypes() {
        UserType userType1 = new UserType(1L, "Administrador", "Administrador do Sistema");
        UserType userType2 = new UserType(2L, "Professor", "Professor do Sistema");

        List<UserType> userTypes = new ArrayList<>();
        userTypes.add(userType1);
        userTypes.add(userType2);

        return userTypes;
    }
}
