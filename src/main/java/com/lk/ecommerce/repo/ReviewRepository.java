package com.lk.ecommerce.repo;

import com.lk.ecommerce.entity.CartItems;
import com.lk.ecommerce.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

}
