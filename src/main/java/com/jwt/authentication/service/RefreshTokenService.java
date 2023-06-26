package com.jwt.authentication.service;

import com.jwt.authentication.entity.RefreshToken;
import com.jwt.authentication.entity.User;
import com.jwt.authentication.repository.RefreshTokenRepository;
import com.jwt.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {

    public final long refreshTokenValidity = 2 * 60 * 1000;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(String username) {

        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("No user found with this " +
                "username !!"));
        RefreshToken refreshToken1 = user.getRefreshToken();

        if (refreshToken1 == null) {
            refreshToken1 = RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expiry(Instant.now().plusMillis(refreshTokenValidity))
                    .user(user)
                    .build();
        } else {
            refreshToken1.setExpiry(Instant.now().plusMillis(refreshTokenValidity));
        }

        user.setRefreshToken(refreshToken1);
        RefreshToken savedRefreshToken = this.refreshTokenRepository.save(refreshToken1);

        return savedRefreshToken;
    }

    public RefreshToken verifyRefreshToken(String refreshToken) {

        RefreshToken refreshTokenOb = this.refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Refresh token does " +
                "not exists !!"));
        if (refreshTokenOb.getExpiry().compareTo(Instant.now()) < 0) {
            this.refreshTokenRepository.delete(refreshTokenOb);
            throw new RuntimeException("Refresh token has expired !!");
        }

        return refreshTokenOb;
    }
}
