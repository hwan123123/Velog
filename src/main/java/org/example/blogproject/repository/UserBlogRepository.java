package org.example.blogproject.repository;

import org.example.blogproject.domain.UserBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBlogRepository extends JpaRepository<UserBlog, Long> {
}