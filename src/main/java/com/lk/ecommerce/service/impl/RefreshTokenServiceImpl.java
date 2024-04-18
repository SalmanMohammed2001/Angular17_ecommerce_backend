package com.lk.ecommerce.service.impl;


import com.lk.ecommerce.entity.RefreshToken;
import com.lk.ecommerce.entity.User;
import com.lk.ecommerce.repo.RefreshTokenRepository;
import com.lk.ecommerce.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;



@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl {

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;
    public String createRefreshToken(String email) {
        User user= userRepository.findByEmail(email);
        RefreshToken refreshToken=refreshTokenRepository.findByUid(user.getUid());
        RefreshToken newRefreshToken;
        if (refreshToken != null) {
            refreshTokenRepository.delete(refreshToken);
        }
        newRefreshToken=refreshTokenRepository.save(new RefreshToken(0,UUID.randomUUID().toString(),Instant.now().plus(Duration.ofDays(30)),user));
        return newRefreshToken.getToken();


    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }


}
