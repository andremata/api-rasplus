package com.client.api.rasplus.mapper;

import com.client.api.rasplus.dto.UserDTO;
import com.client.api.rasplus.model.jpa.SubscriptionsType;
import com.client.api.rasplus.model.jpa.User;
import com.client.api.rasplus.model.jpa.UserType;

public class UserMapper {

    public static User fromDtoToEntity(UserDTO dto, UserType userType, SubscriptionsType subscriptionsType) {
        return User
                .builder()
                .id(dto.getId())
                .name(dto.getName())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .dtSubscription(dto.getDtSubscription())
                .dtExpiration(dto.getDtExpiration())
                .userType(userType)
                .subscriptionsType(subscriptionsType)
                .build();
    }
}
