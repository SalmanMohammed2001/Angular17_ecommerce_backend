package com.lk.ecommerce.dto.core;

import com.lk.ecommerce.eums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDTO {


    private UUID id;
    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;

    private Long discount;
    private UUID trackingId;

    private String userName;
    private List<CartItemDTO> cardItem;

    private String couponName;



}
