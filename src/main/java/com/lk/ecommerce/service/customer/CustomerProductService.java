package com.lk.ecommerce.service.customer;

import com.lk.ecommerce.dto.core.ProductDTO;

import java.util.List;

public interface CustomerProductService {
    public List<ProductDTO> allProduct();
    public List<ProductDTO> findProductByName(String name);
}
