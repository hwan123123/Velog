package org.example.blogproject.repository;

import org.example.blogproject.domain.Comment;
import org.example.blogproject.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostOrderByCreationDate(Post post);
    List<Comment> findByParent(Comment parent);
}