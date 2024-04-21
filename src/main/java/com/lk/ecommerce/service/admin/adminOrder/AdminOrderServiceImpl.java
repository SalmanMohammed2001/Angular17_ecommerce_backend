package com.lk.ecommerce.service.admin.adminOrder;

import com.lk.ecommerce.dto.core.AnalyticsResponse;
import com.lk.ecommerce.dto.core.OrderDTO;
import com.lk.ecommerce.entity.Orders;
import com.lk.ecommerce.eums.OrderStatus;
import com.lk.ecommerce.repo.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    public AnalyticsResponse calculateAnalytics(){
        LocalDate currentDate = LocalDate.now();
        LocalDate previousMonth = currentDate.minusMonths(1);

        Long currentMonthOrder=getTotalOrdersForMonth(currentDate.getMonthValue(),currentDate.getYear());
        Long previousMonthOrder=getTotalOrdersForMonth(previousMonth.getMonthValue(),previousMonth.getYear());
        Long currentMonthEarnings=getTotalEraningForMonth(currentDate.getMonthValue(),currentDate.getYear());
        Long previousMonthEarnings=getTotalEraningForMonth(previousMonth.getMonthValue(),previousMonth.getYear());

        Long place=orderRepository.countByOrderStatus(OrderStatus.PLACED);
        Long shipped=orderRepository.countByOrderStatus(OrderStatus.SHIPPED);
        Long delivered=orderRepository.countByOrderStatus(OrderStatus.DELIVER);

        return  new AnalyticsResponse(
                place,shipped,delivered,currentMonthOrder,previousMonthOrder,currentMonthEarnings,previousMonthEarnings
        );
    }

    private Long getTotalEraningForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);

        Date endMonth = calendar.getTime();

        List<Orders> orders=orderRepository.findByDateBetweenAndOrderStatus(startOfMonth,endMonth,OrderStatus.DELIVER);

        Long sum=0L;
        for(Orders order:orders){
            sum+=order.getAmount();
        }
        return  sum;

    }

    private Long getTotalOrdersForMonth(int month, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,year);
        calendar.set(Calendar.MONTH,month-1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Date startOfMonth = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);

        Date endMonth = calendar.getTime();

        List<Orders> orders=orderRepository.findByDateBetweenAndOrderStatus(startOfMonth,endMonth,OrderStatus.DELIVER);

        return (long) orders.size();


    }
}

