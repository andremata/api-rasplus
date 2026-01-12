package com.client.api.rasplus.service;

import com.client.api.rasplus.model.jpa.UserType;

import java.util.List;

public interface UserTypeService {

    List<UserType> findAll();
}
