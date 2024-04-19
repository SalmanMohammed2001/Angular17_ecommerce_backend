package com.lk.ecommerce.controller.customer;

import com.lk.ecommerce.dto.core.*;
import com.lk.ecommerce.service.customer.CustomerProductService;
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
@RequestMapping("api/v1/cart")
@RequiredArgsConstructor
@CrossOrigin
public class CartController {



    private final CartService  cartService;

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> saveCategory(@RequestBody @Valid AddProductCartDTO addProductCartDTO) {
        try {
            int res = cartService.addToProductCart(addProductCartDTO);
            switch (res) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", ""));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Product Cart Already Defined", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDTO>getCardByUserId(@PathVariable UUID userId){
        try{
            OrderDTO orderDTO = cartService.getCartByUserId(userId);
            if(orderDTO!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Cart By User", orderDTO));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @GetMapping("coupon/{userId}/{code}")
    public ResponseEntity<ResponseDTO>applyCoupon(@PathVariable UUID userId,@PathVariable String code){
        try{
            OrderDTO orderDTO = cartService.applyCoupon(userId,code);
            if(orderDTO!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Apply Coupon", orderDTO));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Coupon Already Exists", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @PostMapping("/addition")
    public ResponseEntity<ResponseDTO>increaseProductQuantity(@RequestBody AddProductCartDTO addProductCartDTO){
        try{
            OrderDTO orderDTO = cartService.increaseProductQuantity(addProductCartDTO);
            if(orderDTO!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Product Increased", orderDTO));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @PostMapping("/deduction")
    public ResponseEntity<ResponseDTO>decreaseProductQuantity(@RequestBody AddProductCartDTO addProductCartDTO){
        try{
            OrderDTO orderDTO = cartService.decreaseProductQuantity(addProductCartDTO);
            if(orderDTO!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Product Increased", orderDTO));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @PostMapping("/placeOrder")
    public ResponseEntity<ResponseDTO>placeOrder(@RequestBody PlaceOrderDTO placeOrderDTO){
        try{
            OrderDTO orderDTO = cartService.placeOrder(placeOrderDTO);
            if(orderDTO!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "Order Placed", orderDTO));
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
