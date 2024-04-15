package com.lk.ecommerce.dto.core;

import jakarta.persistence.Column;

import java.util.UUID;

public class UserDTO  {
    private UUID uid;

    private String email;
    private String password;
    private String name;
    private String role;
}
