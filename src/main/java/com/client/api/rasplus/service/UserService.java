package com.client.api.rasplus.service;

import com.client.api.rasplus.dto.UserDTO;
import com.client.api.rasplus.model.jpa.User;

public interface UserService {

    User create(UserDTO userDTO);
}
