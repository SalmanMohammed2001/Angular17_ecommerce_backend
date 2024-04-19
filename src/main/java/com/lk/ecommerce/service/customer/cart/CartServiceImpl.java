package com.lk.ecommerce.service.customer.cart;

import com.lk.ecommerce.dto.core.AddProductCartDTO;
import com.lk.ecommerce.entity.CartItems;
import com.lk.ecommerce.entity.Orders;
import com.lk.ecommerce.entity.Product;
import com.lk.ecommerce.entity.User;
import com.lk.ecommerce.eums.OrderStatus;
import com.lk.ecommerce.repo.CartItemRepository;
import com.lk.ecommerce.repo.OrderRepository;
import com.lk.ecommerce.repo.ProductRepository;
import com.lk.ecommerce.repo.UserRepository;
import com.lk.ecommerce.util.VarList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    private final CartItemRepository cartItemRepository;

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
}
