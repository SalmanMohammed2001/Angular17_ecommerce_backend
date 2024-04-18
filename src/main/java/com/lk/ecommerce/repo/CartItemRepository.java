package com.lk.ecommerce.repo;

import com.lk.ecommerce.entity.CartItems;
import com.lk.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface CartItemRepository extends JpaRepository<CartItems, UUID> {

    @Query(value = "SELECT * FROM cart_items WHERE product_id=?  AND order_id=? AND user_id=?",nativeQuery = true)
    Optional<CartItems> findByProductAndOrderIdAndUserId(UUID productId,UUID orderId,UUID userId);
}
