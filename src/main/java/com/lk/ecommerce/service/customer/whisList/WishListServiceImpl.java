package com.lk.ecommerce.service.customer.whisList;

import com.lk.ecommerce.dto.core.WishListDto;
import com.lk.ecommerce.entity.Product;
import com.lk.ecommerce.entity.User;
import com.lk.ecommerce.entity.WishList;
import com.lk.ecommerce.repo.ProductRepository;
import com.lk.ecommerce.repo.UserRepository;
import com.lk.ecommerce.repo.WishListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {
    private final UserRepository userRepo;

    private final ProductRepository productRepo;

    private final WishListRepository wishListRepo;

    public WishListDto addProductToWishList(WishListDto requestWishListDto) {
        Optional<Product> optionalProduct = productRepo.findById(requestWishListDto.getProductId());
        Optional<User> optionalUser = userRepo.findById(requestWishListDto.getUserId());

        if (optionalUser.isPresent() && optionalProduct.isPresent()) {
            WishList wishList = new WishList();
            wishList.setProduct(optionalProduct.get());
            wishList.setUser(optionalUser.get());
            return wishListRepo.save(wishList).getwishListDto();
        }
        return null;
    }

    @Override
    public List<WishListDto> getWishListByUserId(UUID userId) {
    // return wishListRepo.findAllByUserUid(userId).stream().map(WishList::getwishListDto).collect(Collectors.toList());
        List<WishList> allByUserUid = wishListRepo.findAllByUserUid(userId);

        List<WishListDto> wishListDtoList=new ArrayList<>();


        for(WishList wishList:allByUserUid) {
            Product product = productRepo.findById(wishList.getProduct().getId()).get();
            WishListDto wishListDto=new WishListDto();
            wishListDto.setId(wishList.getId());
            wishListDto.setProductId(product.getId());
            wishListDto.setReturnedImg(product.getImg());
            wishListDto.setProductName(product.getName());
            wishListDto.setProductDescription(product.getDescription());
            wishListDto.setPrice(product.getPrice());
            wishListDto.setUserId(wishList.getUser().getUid());
            wishListDtoList.add(wishListDto);
        }

        return wishListDtoList;
    }
}
