package com.lk.ecommerce.dto.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDTO {
    private UUID id;

    private String name;

    private String description;
}
