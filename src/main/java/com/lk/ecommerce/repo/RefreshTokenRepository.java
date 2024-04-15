package com.lk.ecommerce.repo;


import com.lk.ecommerce.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * @TimeStamp 2024-02-14 13:43
 * @ProjectDetails RAPTOR-TASK-API
 * @Author udarasan
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    RefreshToken findByToken(String token);

    @Query(value = "SELECT * FROM refresh_token WHERE user_id=?1", nativeQuery = true)
    RefreshToken findByUid(UUID uid);

}
