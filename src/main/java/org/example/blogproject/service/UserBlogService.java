package org.example.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.example.blogproject.domain.Post;
import org.example.blogproject.domain.User;
import org.example.blogproject.repository.PostRepository;
import org.example.blogproject.repository.UserBlogRepository;
import org.example.blogproject.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserBlogService {

    private final UserBlogRepository userBlogRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;


    public User findByUserId(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Post> findPostsByUser(User user) {
        return postRepository.findByUser(user);
    }
}