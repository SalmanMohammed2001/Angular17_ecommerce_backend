package com.lk.ecommerce.dto.core;

import com.lk.ecommerce.eums.UserRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class AuthDTO {
    private UUID uuid;
    private String userRoles;
    private String name;
    private String email;
    private String token;
    private String refreshToken;
}