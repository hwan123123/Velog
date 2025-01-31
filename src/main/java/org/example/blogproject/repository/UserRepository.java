package org.example.blogproject.repository;

import org.example.blogproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    Optional<User> findByProviderAndSocialId(String provider, String socialId);

}