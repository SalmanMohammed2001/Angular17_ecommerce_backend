package com.lk.ecommerce.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDto {



    private UUID id;

    private Long rating;

    private String description;

    private MultipartFile img;

    private byte[] returnImg;


    private UUID userId;

    private UUID productId;

    private String username;





}
