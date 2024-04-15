package com.lk.ecommerce.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @TimeStamp 2024-02-14 17:08
 * @ProjectDetails RAPTOR-TASK-API
 * @Author udarasan
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RefreshTokenDTO {
    private String token;
    private String refreshToken;
}
