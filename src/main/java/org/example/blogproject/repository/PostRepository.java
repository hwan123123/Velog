package org.example.blogproject.repository;

import org.example.blogproject.domain.Blog;
import org.example.blogproject.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByBlogAndTitle(Blog blog, String title);
}