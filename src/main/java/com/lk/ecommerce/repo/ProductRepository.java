package com.lk.ecommerce.repo;

import com.lk.ecommerce.dto.core.ProductDTO;
import com.lk.ecommerce.entity.Category;
import com.lk.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    boolean existsByName(String name);
    List<Product> findByNameContaining(String name);

}
