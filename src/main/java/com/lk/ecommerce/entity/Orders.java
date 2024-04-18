package com.lk.ecommerce.entity;

import com.lk.ecommerce.eums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;

    private Long discount;
    private UUID trackingId;


    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",referencedColumnName = "uid")
    private User user;




    @OneToMany(fetch = FetchType.LAZY,mappedBy = "orders")
    private List<CartItems> cardItem;



}
