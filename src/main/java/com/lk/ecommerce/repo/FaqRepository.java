package com.lk.ecommerce.repo;

import com.lk.ecommerce.entity.CartItems;
import com.lk.ecommerce.entity.FAQ;
import com.lk.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface FaqRepository extends JpaRepository<FAQ, UUID> {

    List<FAQ> findAllByProductId(UUID productId);
}