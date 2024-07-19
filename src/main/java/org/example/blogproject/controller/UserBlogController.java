package org.example.blogproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blogproject.domain.Post;
import org.example.blogproject.domain.PostStatus;
import org.example.blogproject.domain.User;
import org.example.blogproject.service.PostService;
import org.example.blogproject.service.UserBlogService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/velog/{username}")
@RequiredArgsConstructor
@Slf4j
public class UserBlogController {
    private final UserBlogService userBlogService;
    private final PostService postService;

    @GetMapping
    public String showUserBlog(@PathVariable String username, Model model, Principal principal) throws UnsupportedEncodingException {
        if (principal == null) {
            model.addAttribute("errorMessage", "아이디와 비밀번호를 확인해주세요.");
            return "redirect:/velog";
        }

        User loginUser = userBlogService.findByUsername(principal.getName());
        model.addAttribute("loginUser", loginUser);
        List<Post> posts = postService.findAllByUserOrderByReleaseDateDesc(loginUser);
        for(Post post : posts) {
            if(post.getPostStatus() == PostStatus.PUBLISHED) {
                model.addAttribute("posts", posts);
            }
        }

        User blogUser = userBlogService.findByUsername(username);
        model.addAttribute("blogUser", blogUser);

        return "userblog";
    }
}
