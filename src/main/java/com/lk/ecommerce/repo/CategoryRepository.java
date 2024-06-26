package com.lk.ecommerce.repo;

import com.lk.ecommerce.entity.CartItems;
import com.lk.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    boolean existsByName(String name);


}
