package com.lk.ecommerce.service.admin.coupn;

import com.lk.ecommerce.dto.core.CouponDTO;
import com.lk.ecommerce.entity.Coupon;
import com.lk.ecommerce.repo.CouponRepository;
import com.lk.ecommerce.util.VarList;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {


    private final CouponRepository couponRepository;

    private final ModelMapper modelMapper;
    @Override
    public int createCoupon(CouponDTO dto) {
        if(couponRepository.existsByCode(dto.getCode())){
          return   VarList.Not_Acceptable;
        }
        couponRepository.save(modelMapper.map(dto, Coupon.class));
        return VarList.Created;
    }

    @Override
    public List<CouponDTO> getAllCoupon() {
        return (modelMapper.map(couponRepository.findAll(),new TypeToken<List<CouponDTO>>(){}.getType()));
    }
}
