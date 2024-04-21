package com.lk.ecommerce.service.customer;

import com.lk.ecommerce.dto.core.ProductDTO;
import com.lk.ecommerce.dto.core.ProductDetailsDto;

import java.util.List;
import java.util.UUID;

public interface CustomerProductService {
    public List<ProductDTO> allProduct();
    public List<ProductDTO> findProductByName(String name);

    public ProductDetailsDto productDetailsDto(UUID productId);
}
