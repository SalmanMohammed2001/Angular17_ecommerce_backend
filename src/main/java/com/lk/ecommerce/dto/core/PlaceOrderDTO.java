package com.lk.ecommerce.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.rmi.server.UID;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PlaceOrderDTO {
    private UUID userId;
    private String address;
    private String orderDescription;









}
