package com.lk.ecommerce.entity;

import com.lk.ecommerce.dto.core.WishListDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    
    public WishListDto getwishListDto(){
        WishListDto wishListDto = new WishListDto();
        wishListDto.setId(id);
        wishListDto.setProductId(product.getId());
        wishListDto.setReturnedImg(product.getImg());
        wishListDto.setProductName(product.getName());
        wishListDto.setProductDescription(product.getDescription());
        wishListDto.setPrice(product.getPrice());
        wishListDto.setUserId(user.getUid());
        return  wishListDto;
    }
}

