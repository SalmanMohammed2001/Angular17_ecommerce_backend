package com.lk.ecommerce.dto.core;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class FAQDto {
    private UUID id;
    private String question;
    private String answer;
    private UUID productId;
}
