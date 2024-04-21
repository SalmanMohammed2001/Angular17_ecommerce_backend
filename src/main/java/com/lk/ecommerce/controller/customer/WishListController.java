package com.lk.ecommerce.controller.customer;

import com.lk.ecommerce.dto.core.ResponseDTO;
import com.lk.ecommerce.dto.core.ReviewDto;
import com.lk.ecommerce.dto.core.WishListDto;
import com.lk.ecommerce.service.customer.whisList.WishListService;
import com.lk.ecommerce.util.VarList;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/wishList")
@CrossOrigin
@RequiredArgsConstructor
public class WishListController {
    private final WishListService wishListService;
    @PostMapping("/save")
    public ResponseEntity<ResponseDTO>givewReview(@RequestBody WishListDto reviewDto){
        try{
            WishListDto wishListDto = wishListService.addProductToWishList(reviewDto);
            if(wishListDto!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, " wishList", wishListDto));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @GetMapping("/find/{userId}")
    public ResponseEntity<ResponseDTO>useIdGetReview(@PathVariable UUID userId){
        try{
            List<WishListDto> wishListByUserId = wishListService.getWishListByUserId(userId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDTO(VarList.OK, " wishList", wishListByUserId));
            /*if(wishListByUserId!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, " wishList", wishListByUserId));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }*/
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }
}
