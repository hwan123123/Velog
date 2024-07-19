package org.example.blogproject.service;

import lombok.RequiredArgsConstructor;
import org.example.blogproject.domain.Comment;
import org.example.blogproject.domain.Post;
import org.example.blogproject.repository.CommentRepository;
import org.example.blogproject.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 특정 게시물의 댓글을 생성 날짜 순으로 찾기
    public List<Comment> findAllByPostIdOrderByCreationDate(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        return commentRepository.findAllByPostOrderByCreationDate(post);
    }

    // 댓글 저장
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    // 특정 댓글 저장
    public void save(Comment comment) {
        commentRepository.save(comment);
    }

    // 부모 댓글로 답글 찾기
    public List<Comment> findByParent(Comment parent) {
        return commentRepository.findByParent(parent);
    }

    // ID로 댓글 찾기
    public Comment findById(Long id) {
        return commentRepository.findById(id).orElse(null);
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    // 답글 추가
    public Comment addReply(Long postId, Long userId, Long parentId, String content) {
        Comment parentComment = commentRepository.findById(parentId).orElseThrow(() -> new IllegalArgumentException("Invalid parent comment ID"));
        Comment reply = new Comment();
        reply.setPost(parentComment.getPost());
        reply.setUser(parentComment.getUser());
        reply.setParent(parentComment);
        reply.setContent(content);
        return commentRepository.save(reply);
    }
}
