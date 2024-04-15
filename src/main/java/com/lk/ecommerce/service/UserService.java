package com.lk.ecommerce.service;

import com.lk.ecommerce.dto.core.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);

    int saveAdmin(UserDTO userDTO);
}
