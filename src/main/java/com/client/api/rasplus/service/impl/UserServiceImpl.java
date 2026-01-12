package com.client.api.rasplus.service.impl;

import com.client.api.rasplus.dto.UserDTO;
import com.client.api.rasplus.exception.BadRequestException;
import com.client.api.rasplus.exception.NotFoundException;
import com.client.api.rasplus.mapper.UserMapper;
import com.client.api.rasplus.model.jpa.User;
import com.client.api.rasplus.model.jpa.UserType;
import com.client.api.rasplus.repository.jpa.UserRepository;
import com.client.api.rasplus.repository.jpa.UserTypeRepository;
import com.client.api.rasplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public User create(UserDTO userDTO) {
        if(Objects.nonNull(userDTO.getId())) {
            throw new BadRequestException("The ID must be null.");
        }

        var userTypeOpt = userTypeRepository.findById(userDTO.getUserTypeId());

        if(userTypeOpt.isEmpty()) {
            throw new NotFoundException("User Type not found!");
        }

        UserType userType = userTypeOpt.get();

        return userRepository.save(UserMapper.fromDtoToEntity(userDTO, userType, null));
    }
}
