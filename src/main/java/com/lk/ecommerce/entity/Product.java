package com.lk.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lk.ecommerce.dto.core.ProductDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
   @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Long price;

    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "category_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)

    @JsonIgnore
    private Category category;

 public ProductDTO getDto(){
  ProductDTO productDto = new ProductDTO();
  productDto.setId(id);
  productDto.setName(name);
  productDto.setPrice(price);
  productDto.setDescription(description);
  productDto.setByteImage(img);
  productDto.setCategoryId(category.getId());
  productDto.setCategoryName(category.getName());
  return productDto;
 }




}
