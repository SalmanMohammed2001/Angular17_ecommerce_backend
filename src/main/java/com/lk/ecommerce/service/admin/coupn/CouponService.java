package com.lk.ecommerce.service.admin.coupn;

import com.lk.ecommerce.dto.core.CouponDTO;

import java.util.List;

public interface CouponService {

    public int createCoupon(CouponDTO dto);

    public List<CouponDTO> getAllCoupon();

}