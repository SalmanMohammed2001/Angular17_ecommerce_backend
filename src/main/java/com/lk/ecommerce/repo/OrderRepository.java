package com.lk.ecommerce.repo;

import com.lk.ecommerce.entity.Category;
import com.lk.ecommerce.entity.Orders;
import com.lk.ecommerce.eums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface OrderRepository extends JpaRepository<Orders, UUID> {

    Orders findByUserUidAndOrderStatus(UUID userId, OrderStatus orderStatus);
    List<Orders> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);


}
