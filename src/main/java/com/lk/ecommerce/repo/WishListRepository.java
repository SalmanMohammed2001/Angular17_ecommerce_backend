package com.lk.ecommerce.repo;

import com.lk.ecommerce.entity.Product;
import com.lk.ecommerce.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface WishListRepository extends JpaRepository<WishList, Long> {


   // @Query(value = "select * from wish_list where user_id=?1",nativeQuery = true)
    List<WishList> findAllByUserUid(UUID userId);

}
