package org.example.blogproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blogproject.domain.Post;
import org.example.blogproject.domain.User;
import org.example.blogproject.service.PostService;
import org.example.blogproject.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/velog")
@RequiredArgsConstructor
@Slf4j
public class HomeController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping
    public String velog(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        List<Post> posts = postService.findAllByPostStatusNotArchived();
        model.addAttribute("posts", posts);

        if (userDetails != null) {
            User user = userService.findByUsername(userDetails.getUsername());
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", null);
        }

        return "main/home";
    }
}
