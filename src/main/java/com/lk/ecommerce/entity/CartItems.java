package com.lk.ecommerce.entity;

import com.lk.ecommerce.dto.core.CartItemDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long price;

    private Long quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders orders;

    public CartItemDTO getCartDto(){
        CartItemDTO cartItemDto = new CartItemDTO();
        cartItemDto.setId(id);
        cartItemDto.setPrice(price);
        cartItemDto.setProduct(product.getId());
        cartItemDto.setQuantity(quantity);
        cartItemDto.setUser(user.getUid());
        cartItemDto.setProductName(product.getName());
        cartItemDto.setReturnImg(product.getImg());
        return cartItemDto;
    }

}
