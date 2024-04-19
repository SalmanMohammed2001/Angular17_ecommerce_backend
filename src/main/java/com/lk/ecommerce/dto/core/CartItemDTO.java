package com.lk.ecommerce.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItemDTO {


    private UUID id;

    private Long price;

    private Long quantity;


    private UUID product;

    private UUID orderId;

    private String productName;

    private byte[] returnImg;


    private UUID user;


}
