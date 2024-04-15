package com.lk.ecommerce.dto.core;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO  {
    private UUID uid;
    private String email;
    private String password;
    private String name;
    private String role;
}
