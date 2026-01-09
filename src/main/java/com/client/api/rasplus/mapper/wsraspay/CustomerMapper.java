package com.client.api.rasplus.mapper.wsraspay;

import com.client.api.rasplus.dto.wsraspay.CustomerDTO;
import com.client.api.rasplus.model.User;

public class CustomerMapper {

    public static CustomerDTO build(User user) {

        var fullName = user.getName().split(" ");
        var firstName = fullName[0];
        var lastName = fullName.length > 1 ? fullName[fullName.length - 1] : "";

        return CustomerDTO.builder()
                .cpf(user.getCpf())
                .email(user.getEmail())
                .firstName(firstName)
                .lastName(lastName)
                .build();
    }
}
