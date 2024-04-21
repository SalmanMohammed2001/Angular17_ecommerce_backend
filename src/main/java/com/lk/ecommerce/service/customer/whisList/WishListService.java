package com.lk.ecommerce.service.customer.whisList;


import com.lk.ecommerce.dto.core.WishListDto;

import java.util.List;
import java.util.UUID;

public interface WishListService {
    public WishListDto addProductToWishList(WishListDto wishListDto);
    public List<WishListDto> getWishListByUserId(UUID userId);
}
