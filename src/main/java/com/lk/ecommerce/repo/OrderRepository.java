package com.lk.ecommerce.repo;

import com.lk.ecommerce.entity.Category;
import com.lk.ecommerce.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {


}
