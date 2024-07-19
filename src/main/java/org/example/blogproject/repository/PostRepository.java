package org.example.blogproject.repository;

import org.example.blogproject.domain.Post;
import org.example.blogproject.domain.PostStatus;
import org.example.blogproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);

    List<Post> findAllByOrderByReleaseDateDesc();

    List<Post> findAllByUserOrderByReleaseDateDesc(User user);

    List<Post> findAllByPostStatusNotOrderByReleaseDateDesc(PostStatus postStatus);

    void deletePostByTitle(String title);
}