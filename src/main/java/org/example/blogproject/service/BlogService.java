package org.example.blogproject.service;

import org.example.blogproject.domain.Blog;
import org.example.blogproject.domain.User;
import org.example.blogproject.repository.BlogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    @Transactional(readOnly = false)
    public void makeBlog(User user) {
        blogRepository.save(new Blog(user));
    }

    public Blog findByUser(User byUsername) {
        return blogRepository.findByUser(byUsername);
    }
}