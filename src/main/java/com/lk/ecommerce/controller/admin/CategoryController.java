package com.lk.ecommerce.controller.admin;

import com.lk.ecommerce.dto.core.AuthDTO;
import com.lk.ecommerce.dto.core.CategoryDTO;
import com.lk.ecommerce.dto.core.ResponseDTO;
import com.lk.ecommerce.dto.core.UserDTO;
import com.lk.ecommerce.service.UserService;
import com.lk.ecommerce.service.admin.category.CategoryService;
import com.lk.ecommerce.util.JwtUtil;
import com.lk.ecommerce.util.VarList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/category")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryController {

    private final CategoryService categoryService;

    //constructor injection

    @PostMapping(value = "/save")
    public ResponseEntity<ResponseDTO> saveCategory(@RequestBody @Valid CategoryDTO categoryDTO) {
        try {
            int res = categoryService.SaveCategory(categoryDTO);
            switch (res) {
                case VarList.Created -> {
                    return ResponseEntity.status(HttpStatus.CREATED)
                            .body(new ResponseDTO(VarList.Created, "Success", ""));
                }
                case VarList.Not_Acceptable -> {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                            .body(new ResponseDTO(VarList.Not_Acceptable, "Category Name Already Defined", null));
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

    @GetMapping("/list")
    public ResponseEntity<ResponseDTO>findAllCategory(){
        try{
            List<CategoryDTO> categoryDTOList =  categoryService.findAll();
            if(categoryDTOList!=null){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ResponseDTO(VarList.OK, "All Category", categoryDTOList));
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
