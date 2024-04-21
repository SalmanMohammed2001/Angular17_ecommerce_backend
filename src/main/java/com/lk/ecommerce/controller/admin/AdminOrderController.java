package com.lk.ecommerce.controller.admin;

import com.lk.ecommerce.dto.core.AnalyticsResponse;
import com.lk.ecommerce.dto.core.OrderDTO;
import com.lk.ecommerce.dto.core.ProductDTO;
import com.lk.ecommerce.dto.core.ResponseDTO;
import com.lk.ecommerce.service.admin.adminOrder.AdminOrderService;
import com.lk.ecommerce.service.admin.product.AdminProductService;
import com.lk.ecommerce.util.VarList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/admin/placeOrder")
@RequiredArgsConstructor
@CrossOrigin
public class AdminOrderController {



    private final AdminOrderService adminOrderService;





    @GetMapping("/list")
    public ResponseEntity<ResponseDTO>findAllCategory(){
        try{
            List<OrderDTO> allPlacedOrder = adminOrderService.getAllPlacedOrder();
            if(allPlacedOrder!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "All Place Order", allPlacedOrder));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @GetMapping("/changeOrderStatus/{orderId}/{status}")
    public ResponseEntity<ResponseDTO>changeOrderStatus(@PathVariable UUID orderId,@PathVariable String status){
        try{
            OrderDTO orderDTO = adminOrderService.changeOrderStatus(orderId, status);
            if(orderDTO!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "SuccessFul Change Order Status", orderDTO));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @GetMapping("/order/analytics")
    public ResponseEntity<ResponseDTO>getAnalytics(){
        try{
            AnalyticsResponse analyticsResponse = adminOrderService.calculateAnalytics();
            if(analyticsResponse!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "All Place Order", analyticsResponse));
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
