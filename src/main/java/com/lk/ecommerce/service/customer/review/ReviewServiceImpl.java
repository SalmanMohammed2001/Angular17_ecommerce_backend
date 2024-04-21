package com.lk.ecommerce.service.customer.review;

import com.lk.ecommerce.dto.core.OrderProductsResponseDto;
import com.lk.ecommerce.dto.core.ProductDTO;
import com.lk.ecommerce.dto.core.ReviewDto;
import com.lk.ecommerce.entity.*;
import com.lk.ecommerce.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl  implements ReviewService {

    public final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final ReviewRepository reviewRepo;
    private final UserRepository userRepo;
    private final CartItemRepository cartItemRepository;

    public OrderProductsResponseDto orderProductsResponseDto(UUID orderId) {
        System.out.println(orderId);
        try {
            Optional<Orders> optionalOrder = orderRepo.findById(orderId);
            OrderProductsResponseDto orderProductsResponseDto = new OrderProductsResponseDto();
            if (optionalOrder.isPresent()) {

                orderProductsResponseDto.setOrderAmount(optionalOrder.get().getAmount());

                List<ProductDTO> productDtoList = new ArrayList<>();
                cartItemRepository.findByOrderId(optionalOrder.get().getId()).forEach(e->{
                    ProductDTO productDto = new ProductDTO();
                    productDto.setId(e.getProduct().getId());
                    productDto.setName(e.getProduct().getName());
                    productDto.setDescription(e.getProduct().getDescription());
                    productDto.setPrice(e.getProduct().getPrice());
                    productDto.setQuantity(e.getQuantity());
                    productDto.setByteImage(e.getProduct().getImg());
                    productDtoList.add(productDto);

                    orderProductsResponseDto.setProductDtos(productDtoList);
                });

            }


            return orderProductsResponseDto;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public ReviewDto geviewReview(ReviewDto reviewDto) throws IOException {
        Optional<Product> optionProduct = productRepo.findById(reviewDto.getProductId());
        Optional<User> optionalUser = userRepo.findById(reviewDto.getUserId());

        if(optionalUser.isPresent() && optionProduct.isPresent()){
            Review review = new Review();
            review.setRating(reviewDto.getRating());
            review.setDescription(reviewDto.getDescription());
            review.setUser(optionalUser.get());
            review.setProduct(optionProduct.get());
            review.setImg(reviewDto.getImg().getBytes());

             return reviewRepo.save(review).getDto();
        }
        return null;
    }

    }

