package org.example.blogproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blogproject.domain.Comment;
import org.example.blogproject.domain.Post;
import org.example.blogproject.domain.User;
import org.example.blogproject.service.CommentService;
import org.example.blogproject.service.PostService;
import org.example.blogproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/velog/{username}/post")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

    private static final String UPLOAD_DIR = "src/main/resources/static/images/";
    private static final int THUMBNAIL_WIDTH = 300;
    private static final int THUMBNAIL_HEIGHT = 300;

    @GetMapping
    public String showUserBlog(@PathVariable String username, Model model) {
        User user = userService.findByUsername(username);
        model.addAttribute("username", user.getUsername());
        return "posts/post";
    }

    @PostMapping
    public String postContent(@PathVariable String username, @ModelAttribute Post post) throws IOException {

        MultipartFile thumbnailImage = post.getThumbnailImageFile();
        if (thumbnailImage != null && !thumbnailImage.isEmpty()) {
            String filename = UUID.randomUUID().toString() + "_" + thumbnailImage.getOriginalFilename();
            Path imagePath = Paths.get(UPLOAD_DIR + filename);
            Files.createDirectories(imagePath.getParent());

            Files.write(imagePath, thumbnailImage.getBytes());
            post.setThumbnailImage(filename);
        } else {
            post.setThumbnailImage("default.png");
        }
        postService.savePost(username, post);
        return "redirect:/velog/" + username;
    }

    @GetMapping("/{postId}/{encodedTitle}")
    public String showPostDetail(@PathVariable String username, @PathVariable String encodedTitle, Model model, @PathVariable String postId){
        User loginUser = userService.findByUsername(username);
        Post post = postService.findById(Long.valueOf(postId));
        List<Comment> allComments = commentService.findAllByPostIdOrderByCreationDate(post.getId());

        model.addAttribute("loginUser", loginUser);
        model.addAttribute("post", post);
        model.addAttribute("comments", allComments);
        model.addAttribute("username", username);
        return "posts/postdetail";
    }

    @PostMapping("/{postId}/{encodedTitle}/comment")
    public String postComment(@PathVariable String username, @PathVariable Long postId, @PathVariable String encodedTitle, @ModelAttribute Comment comment, @RequestParam(required = false) Long parentId, Principal principal) {
        Post post = postService.findById(postId);
        if (parentId != null) {
            Comment parentComment = commentService.findById(parentId);
            comment.setParent(parentComment);
        }
        comment.setPost(post);
        User commentUser = userService.findByUsername(principal.getName());
        comment.setUser(commentUser);
        comment.setAuthor(commentUser.getUsername());
        commentService.saveComment(comment);
        return "redirect:/velog/" + username + "/post/" + postId + "/" + encodedTitle;
    }

    @GetMapping("/{postId}/{encodedTitle}/edit")
    public String showEditPostForm(@PathVariable String username, @PathVariable String encodedTitle, Model model, @PathVariable String postId) {
        Post post = postService.findById(Long.valueOf(postId));
        model.addAttribute("post", post);
        model.addAttribute("username", username);

        return "posts/postedit";
    }

    @PostMapping("/{postId}/edit")
    public String editPost(@PathVariable String username, @ModelAttribute Post updatePost, @PathVariable String postId) {
        Post post = postService.findById(Long.valueOf(postId));
        postService.updatePost(post, updatePost);
        return "redirect:/velog/" + username;
    }

    @DeleteMapping("/{postId}/delete")
    public void deletePost(@PathVariable String postId) {
        log.info("Delete post id " + postId);
        postService.deletePostById(Long.valueOf(postId));
    }
}