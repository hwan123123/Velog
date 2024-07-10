package org.example.blogproject.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.example.blogproject.domain.Post;
import org.example.blogproject.service.PostService;
import org.jsoup.Jsoup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;

    @GetMapping("/")
    public String home(Model model, @RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "12") int size, @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails != null) {
            model.addAttribute("loginUser", userDetails.getUsername());
        }

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());

        Page<Post> posts = postService.findAllPosts(pageable);

        List<Post> removeTagPosts = posts.stream().map(post -> {
            String removeTagContent = Jsoup.parse(post.getContent()).text();
            post.setContent(removeTagContent);
            return post;
        }).collect(Collectors.toList());

        Page<Post> removeTagePostPage = new PageImpl<>(removeTagPosts, pageable, posts.getTotalElements());

        model.addAttribute("posts", removeTagePostPage);
        model.addAttribute("currentPage", page);

        return "pages/main/home";
    }

    @GetMapping("/welcome")
    public String welcome(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = null;
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        }

        model.addAttribute("loginUser", username);

        return "pages/main/welcome";
    }
}