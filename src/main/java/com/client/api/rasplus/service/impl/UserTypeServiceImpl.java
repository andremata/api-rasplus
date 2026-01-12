package com.client.api.rasplus.service.impl;

import com.client.api.rasplus.model.jpa.UserType;
import com.client.api.rasplus.repository.jpa.UserTypeRepository;
import com.client.api.rasplus.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserTypeServiceImpl implements UserTypeService {

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Override
    public List<UserType> findAll() {
        return userTypeRepository.findAll();
    }
}
