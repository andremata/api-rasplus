package com.client.api.rasplus.mapper;

import com.client.api.rasplus.dto.UserDTO;
import com.client.api.rasplus.model.SubscriptionsType;
import com.client.api.rasplus.model.User;
import com.client.api.rasplus.model.UserType;

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
