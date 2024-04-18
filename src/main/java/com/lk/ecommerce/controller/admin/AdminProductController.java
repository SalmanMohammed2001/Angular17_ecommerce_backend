package com.lk.ecommerce.controller.admin;

import com.lk.ecommerce.dto.core.CategoryDTO;
import com.lk.ecommerce.dto.core.ProductDTO;
import com.lk.ecommerce.dto.core.ResponseDTO;
import com.lk.ecommerce.service.admin.category.CategoryService;
import com.lk.ecommerce.service.admin.product.AdminProductService;
import com.lk.ecommerce.util.VarList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
@CrossOrigin
public class AdminProductController {



    private final AdminProductService adminProductService;

    //constructor injection

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> saveCategory(@ModelAttribute @Valid ProductDTO productDTO) {
        System.out.println(productDTO.getCategoryId());
        try {
            int res = adminProductService.saveProduct(productDTO);
            if(res==VarList.Created){
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new ResponseDTO(VarList.Created, "Product Saved", ""));
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                        .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, e.getMessage(), null));
        }
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO>findAllCategory(){
        try{
            List<ProductDTO> productDTOSList =  adminProductService.allProduct();
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
            List<ProductDTO> productDTOSList =  adminProductService.findProductByName(name);
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
