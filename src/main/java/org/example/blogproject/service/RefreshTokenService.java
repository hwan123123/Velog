package org.example.blogproject.service;

import java.util.Optional;

import org.example.blogproject.repository.RefreshTokenRepository;
import org.example.blogproject.domain.RefreshToken;
import org.example.blogproject.domain.User;
import org.example.blogproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = false)
    public RefreshToken addRefreshToken(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public Optional<RefreshToken> findRefreshTokenByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    @Transactional(readOnly = false)
    public void deleteRefreshToken(String username) {
        User user = userRepository.findByUsername(username);
        refreshTokenRepository.deleteByUserId(user.getId());
    }
}