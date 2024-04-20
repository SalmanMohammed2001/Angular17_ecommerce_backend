package com.lk.ecommerce.controller.admin;

import com.lk.ecommerce.dto.core.CategoryDTO;
import com.lk.ecommerce.dto.core.FAQDto;
import com.lk.ecommerce.dto.core.ProductDTO;
import com.lk.ecommerce.dto.core.ResponseDTO;
import com.lk.ecommerce.service.admin.category.CategoryService;
import com.lk.ecommerce.service.admin.faq.FAQService;
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
@RequestMapping("api/v1/product")
@RequiredArgsConstructor
@CrossOrigin
public class AdminProductController {



    private final AdminProductService adminProductService;

    private final FAQService faqService;

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
    @DeleteMapping(value = "/delete",params = {"id"})
    public ResponseEntity<ResponseDTO>deleteProduct(@RequestParam UUID id){
        try{
            int res = adminProductService.delete(id);
            switch (res) {
                case VarList.No_Content -> {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT)
                            .body(new ResponseDTO(VarList.No_Content, "Delete Success", ""));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Product id can't find", null));
                }
                default -> {
                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                            .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
                }
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                    .body(new ResponseDTO(VarList.Bad_Gateway, "Error", null));
        }
    }

    @PostMapping("/postFaq/{productId}")
    public ResponseEntity<ResponseDTO>postFaq(@PathVariable UUID productId, @RequestBody FAQDto faqDto){
        try{
            FAQDto postedFAQ = faqService.postFAQ(productId, faqDto);
            if(postedFAQ!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "All Product", postedFAQ));
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
