package com.lk.ecommerce.service.customer.cart;

import com.lk.ecommerce.dto.core.AddProductCartDTO;
import com.lk.ecommerce.dto.core.OrderDTO;

import java.util.UUID;

public interface CartService {
    public int addToProductCart(AddProductCartDTO addProductCartDTO);

    public OrderDTO getCartByUserId(UUID userId);

    public OrderDTO applyCoupon(UUID userId, String code);

    public OrderDTO increaseProductQuantity(AddProductCartDTO addProductCartDTO);
    public OrderDTO decreaseProductQuantity(AddProductCartDTO addProductCartDTO);
}
