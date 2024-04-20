package com.lk.ecommerce.service.admin.adminOrder;

import com.lk.ecommerce.dto.core.OrderDTO;
import com.lk.ecommerce.entity.Orders;
import com.lk.ecommerce.eums.OrderStatus;
import com.lk.ecommerce.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;
    public List<OrderDTO> getAllPlacedOrder() {
        List<Orders> ordersList = orderRepository.
        findAllByOrderStatusIn(List.of(OrderStatus.PLACED, OrderStatus.SHIPPED, OrderStatus.DELIVER));

        List<OrderDTO> list=new ArrayList<>();
     /*   for(Orders data:ordersList){
            OrderDTO orderDTO=new OrderDTO();
            orderDTO.setId(data.getId());
            orderDTO.setOrderStatus(data.getOrderStatus());
            orderDTO.setUserName(data.getUser().getName());
            orderDTO.setTotalAmount(data.getTotalAmount());
            orderDTO.setDiscount(data.getDiscount());
            orderDTO.setAmount(data.getAmount());
            orderDTO.setOrderDescription(data.getOrderDescription());
            orderDTO.setDate(data.getDate());
            orderDTO.setAddress(data.getAddress());
            orderDTO.setPayment(data.getPayment());
            orderDTO.setTrackingId(data.getTrackingId());
            orderDTO.setAmount(data.getAmount());
            list.add(orderDTO);
        }

        return list;
*/
      return   ordersList.stream().map(Orders::getOrderDto).collect(Collectors.toList());

    }

    public  OrderDTO changeOrderStatus(UUID orderId,String status){
        Optional<Orders> optionalOrder = orderRepository.findById(orderId);

        if(optionalOrder.isPresent()){
            Orders orders = optionalOrder.get();

            if(Objects.equals(status,"Shipped")){
                orders.setOrderStatus(OrderStatus.SHIPPED);
            }else if (Objects.equals(status,"Deliver")){
                orders.setOrderStatus(OrderStatus.DELIVER);
            }
           return orderRepository.save(orders).getOrderDto();
        }
        return null;
    }
}
