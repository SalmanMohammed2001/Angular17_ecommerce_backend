package com.lk.ecommerce.controller.customer;

import com.lk.ecommerce.dto.core.AddProductCartDTO;
import com.lk.ecommerce.dto.core.OrderDTO;
import com.lk.ecommerce.dto.core.PlaceOrderDTO;
import com.lk.ecommerce.dto.core.ResponseDTO;
import com.lk.ecommerce.service.customer.cart.CartService;
import com.lk.ecommerce.util.VarList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/track")
@RequiredArgsConstructor
@CrossOrigin
public class TrackingController {



    private final CartService cartService;


    @GetMapping("find/{trackingId}")
    public ResponseEntity<ResponseDTO>searchByTrackingID(@PathVariable UUID trackingId){
        try{
            OrderDTO orderDTO = cartService.searchByTrackingId(trackingId);
            if(orderDTO!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "tracking details", orderDTO));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Coupon Already Exists", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }



}
