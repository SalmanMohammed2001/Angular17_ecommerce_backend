package com.lk.ecommerce.service.customer;

import com.lk.ecommerce.dto.core.ProductDTO;
import com.lk.ecommerce.entity.Product;
import com.lk.ecommerce.repo.CategoryRepository;
import com.lk.ecommerce.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    public List<ProductDTO> allProduct() {
        List<Product> allProduct = productRepository.findAll();
        return allProduct.stream().map(Product::getDto).collect(Collectors.toList());
        //  return modelMapper.map(allProduct,new TypeToken<List<ProductDTO>>(){}.getType());
    }

    @Override
    public List<ProductDTO> findProductByName(String name) {
        List<Product> byNameContaining = productRepository.findByNameContaining(name);
        return byNameContaining.stream().map(Product::getDto).collect(Collectors.toList());
    }
}
