package com.lk.ecommerce.dto.core;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CouponDTO {

    private UUID id;

    private String name;

    private String code;

    private Long discount;

    private Date expirationDate;
    

}
