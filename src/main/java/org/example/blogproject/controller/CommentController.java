package org.example.blogproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.blogproject.domain.Comment;
import org.example.blogproject.domain.Post;
import org.example.blogproject.domain.User;
import org.example.blogproject.service.CommentService;
import org.example.blogproject.service.PostService;
import org.example.blogproject.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    @DeleteMapping("/velog/comment/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/velog/reply/{replyId}/delete")
    public ResponseEntity<Void> deleteReply(@PathVariable Long replyId) {
        commentService.deleteComment(replyId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/velog/comment")
    public ResponseEntity<Comment> addComment(@RequestParam Long postId, @RequestParam Long userId, @RequestParam String content) {
        Post post = postService.findById(postId);
        User user = userService.findById(userId);
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);
        commentService.saveComment(comment);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/velog/reply")
    public ResponseEntity<Comment> addReply(@RequestParam Long postId, @RequestParam Long userId, @RequestParam Long parentId, @RequestParam String content) {
        Comment reply = commentService.addReply(postId, userId, parentId, content);
        return ResponseEntity.ok(reply);
    }

    @GetMapping("/velog/comments")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@RequestParam Long postId) {
        List<Comment> comments = commentService.findAllByPostIdOrderByCreationDate(postId);
        return ResponseEntity.ok(comments);
    }
}
