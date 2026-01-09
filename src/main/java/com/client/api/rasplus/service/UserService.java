package com.client.api.rasplus.service;

import com.client.api.rasplus.dto.UserDTO;
import com.client.api.rasplus.model.User;

public interface UserService {

    User create(UserDTO userDTO);
}
