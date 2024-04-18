package com.lk.ecommerce.controller.customer;

import com.lk.ecommerce.dto.core.ProductDTO;
import com.lk.ecommerce.dto.core.ResponseDTO;
import com.lk.ecommerce.service.admin.product.AdminProductService;
import com.lk.ecommerce.service.customer.CustomerProductService;
import com.lk.ecommerce.util.VarList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
@CrossOrigin
public class CustomerProductController {



    private final CustomerProductService customerProductService;

    //constructor injection


    @GetMapping("/list")
    public ResponseEntity<ResponseDTO>findAllCategory(){
        try{
            List<ProductDTO> productDTOSList =  customerProductService.allProduct();
            if(productDTOSList!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "All Product", productDTOSList));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @GetMapping("search/{name}")
    public ResponseEntity<ResponseDTO>findAllByName(@PathVariable String name){
        try{
            List<ProductDTO> productDTOSList =  customerProductService.findProductByName(name);
            if(productDTOSList!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "All Product", productDTOSList));
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
