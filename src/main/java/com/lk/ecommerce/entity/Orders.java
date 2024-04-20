package com.lk.ecommerce.entity;

import com.lk.ecommerce.dto.core.OrderDTO;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;

    private Long discount;
    private UUID trackingId;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",referencedColumnName = "uid")
    private User user;


    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id",referencedColumnName = "id")
    private Coupon coupon;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "orders")
    private List<CartItems> cardItem;

    public OrderDTO getOrderDto(){
        OrderDTO orderDto = new OrderDTO();
        orderDto.setId(id);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setTotalAmount(totalAmount);
        orderDto.setDiscount(discount);
        orderDto.setAddress(address);
        orderDto.setTrackingId(trackingId);
        orderDto.setAmount(amount);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setUserName(user.getName());
        if(coupon !=null){
            orderDto.setCouponName(coupon.getName());
        }
        return  orderDto;
    }


}
