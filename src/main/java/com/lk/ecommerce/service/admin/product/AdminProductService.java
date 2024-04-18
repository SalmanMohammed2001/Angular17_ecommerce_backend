package com.lk.ecommerce.service.admin.product;

import com.lk.ecommerce.dto.core.ProductDTO;

import java.io.IOException;
import java.util.List;

public interface AdminProductService {

     int saveProduct(ProductDTO productDTO) throws IOException;

     public List<ProductDTO> allProduct();
     public List<ProductDTO> findProductByName(String name);


}