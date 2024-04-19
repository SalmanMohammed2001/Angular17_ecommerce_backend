package com.lk.ecommerce.service.customer.cart;

import com.lk.ecommerce.dto.core.AddProductCartDTO;
import com.lk.ecommerce.dto.core.CartItemDTO;
import com.lk.ecommerce.dto.core.OrderDTO;
import com.lk.ecommerce.dto.core.PlaceOrderDTO;
import com.lk.ecommerce.entity.*;
import com.lk.ecommerce.eums.CouponActiveState;
import com.lk.ecommerce.eums.OrderStatus;
import com.lk.ecommerce.exception.ValidationException;
import com.lk.ecommerce.repo.*;
import com.lk.ecommerce.util.VarList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final CartItemRepository cartItemRepository;

    private final CouponRepository couponRepository;

    public int addToProductCart(AddProductCartDTO addProductCartDTO) {
        Orders activeOrder = orderRepository.findByUserUidAndOrderStatus(addProductCartDTO.getUserId(), OrderStatus.PENDING);
        Optional<CartItems> optionalCardItem = cartItemRepository.findByProductAndOrderIdAndUserId(addProductCartDTO.getProductId()
                , activeOrder.getId(), addProductCartDTO.getUserId());

        if (optionalCardItem.isPresent()) {
            return VarList.Not_Acceptable;
        } else {
            Optional<User> optionUser = userRepository.findById(addProductCartDTO.getUserId());
            Optional<Product> optionalProduct = productRepository.findById(addProductCartDTO.getProductId());

            if (optionUser.isPresent() && optionalProduct.isPresent()) {
                CartItems cartItems = new CartItems();
                cartItems.setProduct(optionalProduct.get());
                cartItems.setUser(optionUser.get());
                cartItems.setQuantity(1L);
                cartItems.setPrice(optionalProduct.get().getPrice());
                cartItems.setOrders(activeOrder);
                CartItems saveCartItem = cartItemRepository.save(cartItems);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cartItems.getPrice());
                activeOrder.setAmount(activeOrder.getAmount() + cartItems.getPrice());
                activeOrder.getCardItem().add(cartItems);

                orderRepository.save(activeOrder);

                return VarList.Created;
            }
        }
        return 0;
    }

    @Override
    public OrderDTO getCartByUserId(UUID userId) {
        Orders activeOrder = orderRepository.findByUserUidAndOrderStatus(userId, OrderStatus.PENDING);
        List<CartItemDTO> cartItemDTOS =  activeOrder.getCardItem().stream().map(CartItems::getCartDto).collect(Collectors.toList());
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setAmount(activeOrder.getAmount());
        orderDTO.setId(activeOrder.getId());
        orderDTO.setOrderStatus(activeOrder.getOrderStatus());
        orderDTO.setDiscount(activeOrder.getDiscount());
        orderDTO.setTotalAmount(activeOrder.getTotalAmount());
        orderDTO.setCardItem(cartItemDTOS);
        if(activeOrder.getCoupon()!=null){
            orderDTO.setCouponName(activeOrder.getOrderDto().getCouponName());
        }
        return orderDTO;
    }

    @Override
    public OrderDTO applyCoupon(UUID userId, String code) {
        Orders activeOrder = orderRepository.findByUserUidAndOrderStatus(userId, OrderStatus.PENDING);
        Coupon coupon = couponRepository.findByCode(code).orElseThrow(() -> new ValidationException("Coupon Not Found"));

        System.out.println(coupon.getActiveState());

        if (coupon.getActiveState()== CouponActiveState.DEACTIVATE){
            if(couponExpired(coupon)){
                throw new ValidationException("Coupon has expired");
            }

            double discountAmount=(coupon.getDiscount() /100.00) * activeOrder.getTotalAmount();
            double netAmount=activeOrder.getAmount()-discountAmount;

            System.out.println(discountAmount);
            activeOrder.setAmount((long) netAmount);
            activeOrder.setDiscount((long) discountAmount);
            activeOrder.setCoupon(coupon);
            coupon.setActiveState(CouponActiveState.ACTIVE);
            couponRepository.save(coupon);

            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }else {
            throw new ValidationException("Coupon Already User");
        }


    }

    private boolean couponExpired(Coupon coupon){
        Date currentDate=new Date();
        Date expirationDate = coupon.getExpirationDate();
        return expirationDate != null && currentDate.after(expirationDate);
    }


    public OrderDTO increaseProductQuantity(AddProductCartDTO addProductCartDTO){
        Orders  activeOrder = orderRepository.findByUserUidAndOrderStatus(addProductCartDTO.getUserId(), OrderStatus.PENDING);
        Optional<Product> optionalProduct = productRepository.findById(addProductCartDTO.getProductId());

        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductAndOrderIdAndUserId(addProductCartDTO.getProductId(), activeOrder.getId(), addProductCartDTO.getUserId());

        if(optionalCartItems.isPresent() && optionalProduct.isPresent()){

            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount()+ product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity() + 1);

            if(activeOrder.getCoupon() !=null){
                double discountAmount=(activeOrder.getCoupon().getDiscount() /100.00) * activeOrder.getTotalAmount();
                double netAmount=activeOrder.getAmount()-discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return  null;
    }

    public OrderDTO decreaseProductQuantity(AddProductCartDTO addProductCartDTO){
        Orders  activeOrder = orderRepository.findByUserUidAndOrderStatus(addProductCartDTO.getUserId(), OrderStatus.PENDING);
        Optional<Product> optionalProduct = productRepository.findById(addProductCartDTO.getProductId());

        Optional<CartItems> optionalCartItems = cartItemRepository.findByProductAndOrderIdAndUserId(addProductCartDTO.getProductId(), activeOrder.getId(), addProductCartDTO.getUserId());

        if(optionalCartItems.isPresent() && optionalProduct.isPresent()){

            CartItems cartItems = optionalCartItems.get();
            Product product = optionalProduct.get();

            activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
            activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

            cartItems.setQuantity(cartItems.getQuantity() - 1);

            if (activeOrder.getCoupon() != null) {
                double discountAmount = ((activeOrder.getCoupon().getDiscount() / 100.00) * activeOrder.getTotalAmount());
                double netAmount = activeOrder.getAmount() - discountAmount;

                activeOrder.setAmount((long) netAmount);
                activeOrder.setDiscount((long) discountAmount);
            }

            cartItemRepository.save(cartItems);
            orderRepository.save(activeOrder);
            return activeOrder.getOrderDto();
        }
        return  null;
    }

    @Override
    public OrderDTO placeOrder(PlaceOrderDTO placeOrderDto) {
        Orders activateOrder = orderRepository.findByUserUidAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.PENDING);
        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());

        if (optionalUser.isPresent()){
            activateOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activateOrder.setAddress(placeOrderDto.getAddress());
            activateOrder.setDate(new Date());
            activateOrder.setOrderStatus(OrderStatus.PLACED);
            activateOrder.setTrackingId(UUID.randomUUID());
            orderRepository.save(activateOrder);

            Orders orders = new Orders();
            orders.setAmount(0L);
            orders.setTotalAmount(0L);
            orders.setDiscount(0L);
            orders.setUser(optionalUser.get());
            orders.setOrderStatus(OrderStatus.PENDING);
            orderRepository.save(orders);

            return activateOrder.getOrderDto();
        }
        return  null;
    }

}
