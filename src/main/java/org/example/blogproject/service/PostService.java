package org.example.blogproject.service;

import java.time.LocalDateTime;

import org.example.blogproject.domain.Blog;
import org.example.blogproject.domain.User;
import org.example.blogproject.repository.BlogRepository;
import org.example.blogproject.repository.PostRepository;
import org.example.blogproject.repository.UserRepository;
import org.example.blogproject.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final PostRepository postRepository;

    public void savePost(Post post, UserDetails userDetails) {
        post.setRegistrationDate(LocalDateTime.now());

        User user = userRepository.findByUsername(userDetails.getUsername());
        post.setBlog(blogRepository.findByUser(user));

        postRepository.save(post);
    }

    public Page<Post> findAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post findByBlogAndTitle(Blog blog, String title) {
        return postRepository.findByBlogAndTitle(blog, title);
    }
}