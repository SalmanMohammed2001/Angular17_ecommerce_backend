package com.lk.ecommerce.service.customer;

import com.lk.ecommerce.dto.core.ProductDTO;
import com.lk.ecommerce.dto.core.ProductDetailsDto;
import com.lk.ecommerce.entity.FAQ;
import com.lk.ecommerce.entity.Product;
import com.lk.ecommerce.entity.Review;
import com.lk.ecommerce.repo.CategoryRepository;
import com.lk.ecommerce.repo.FaqRepository;
import com.lk.ecommerce.repo.ProductRepository;
import com.lk.ecommerce.repo.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final FaqRepository faqRepository;

    private final ReviewRepository reviewRepository;

    @Override
    public List<ProductDTO> allProduct() {
        List<Product> allProduct = productRepository.findAll();
        return allProduct.stream().map(Product::getDto).collect(Collectors.toList());
        //  return modelMapper.map(allProduct,new TypeToken<List<ProductDTO>>(){}.getType());
    }

    @Override
    public List<ProductDTO> findProductByName(String name) {
        List<Product> byNameContaining = productRepository.findByNameContaining(name);
        return byNameContaining.stream().map(Product::getDto).collect(Collectors.toList());
    }

    public ProductDetailsDto productDetailsDto( UUID productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()){
            List<FAQ> faqList=   faqRepository.findAllByProductId(productId);
            List<Review> reviewList=   reviewRepository.findAllByProductId(productId);
            ProductDetailsDto productDetailsDto = new ProductDetailsDto();
            productDetailsDto.setProductDto(optionalProduct.get().getDto());
            productDetailsDto.setFaqDtoList(faqList.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));
            productDetailsDto.setReviewDtoList(reviewList.stream().map(Review::getDto).collect(Collectors.toList()));
            return  productDetailsDto;
        }
        return null;
    }
}
