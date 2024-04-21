package com.lk.ecommerce.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDetailsDto {


    private ProductDTO productDto;

   private List<ReviewDto> reviewDtoList;

   private List<FAQDto> faqDtoList;



}
