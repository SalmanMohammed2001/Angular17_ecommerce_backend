package com.lk.ecommerce.service.customer.review;


import com.lk.ecommerce.dto.core.OrderProductsResponseDto;
import com.lk.ecommerce.dto.core.ReviewDto;

import java.io.IOException;
import java.rmi.server.UID;
import java.util.UUID;

public interface ReviewService {
    public OrderProductsResponseDto orderProductsResponseDto(UUID orderId);

    public ReviewDto geviewReview(ReviewDto reviewDto) throws IOException;
}
