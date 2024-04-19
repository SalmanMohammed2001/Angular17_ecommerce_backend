package com.lk.ecommerce.repo;

import com.lk.ecommerce.entity.Coupon;
import com.lk.ecommerce.entity.User;
import com.lk.ecommerce.eums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    boolean existsByCode(String code);

    Optional<Coupon> findByCode(String code);


}
