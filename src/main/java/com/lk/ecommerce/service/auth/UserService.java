package com.lk.ecommerce.service.auth;

import com.lk.ecommerce.dto.core.UserDTO;

public interface UserService {
    int saveUser(UserDTO userDTO);

    int saveAdmin(UserDTO userDTO);
}
