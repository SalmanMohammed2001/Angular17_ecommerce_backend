package com.lk.ecommerce.service.customer.cart;

import com.lk.ecommerce.dto.core.AddProductCartDTO;

public interface CartService {
    public int addToProductCart(AddProductCartDTO addProductCartDTO);
}
