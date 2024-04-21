package com.lk.ecommerce.dto.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lk.ecommerce.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDTO {

    private UUID id;

    private String name;

    private Long price;

    private String description;

    private byte[] byteImage;

    private UUID categoryId;

    private String categoryName;

    private MultipartFile img;

    private Long quantity;
}
