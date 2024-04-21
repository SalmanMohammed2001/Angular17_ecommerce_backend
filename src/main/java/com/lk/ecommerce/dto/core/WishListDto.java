package com.lk.ecommerce.dto.core;

import lombok.Data;

import java.rmi.server.UID;
import java.util.UUID;

@Data
public class WishListDto {

    private Long id;

    private UUID userId;

    private UUID productId;

    private String productName;

    private String productDescription;

    private byte[] returnedImg;

    private Long price;
}
