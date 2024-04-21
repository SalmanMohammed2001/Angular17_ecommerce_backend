package com.lk.ecommerce.controller.customer;

import com.lk.ecommerce.dto.core.*;
import com.lk.ecommerce.service.customer.review.ReviewService;
import com.lk.ecommerce.util.VarList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/review")
@CrossOrigin
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;


    @GetMapping("{orderId}")

    public ResponseEntity<ResponseDTO>getOrderProductsResponseDto(@PathVariable UUID orderId){
        try{
            OrderProductsResponseDto orderProductsResponseDto = reviewService.orderProductsResponseDto(orderId);
            if(orderProductsResponseDto!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "order Products Response Data", orderProductsResponseDto));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<ResponseDTO>givewReview(@ModelAttribute ReviewDto reviewDto){
        try{
            ReviewDto reviewDto1 =reviewService.geviewReview(reviewDto);
            if(reviewDto1!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, " givew Review", reviewDto1));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }
}
