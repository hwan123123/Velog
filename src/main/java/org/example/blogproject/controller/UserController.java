package org.example.blogproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.blogproject.domain.Post;
import org.example.blogproject.domain.User;
import org.example.blogproject.dto.UserLoginDto;
import org.example.blogproject.jwt.util.JwtTokenizer;
import org.example.blogproject.service.PostService;
import org.example.blogproject.service.RefreshTokenService;
import org.example.blogproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/velog")
@Slf4j
public class UserController {
    private final UserService userService;
    private final JwtTokenizer jwtTokenizer;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final PostService postService;

    @GetMapping("/loginform")
    public String showLoginForm(Model model) {
        model.addAttribute("loginDto", new UserLoginDto());
        return "users/loginform";
    }

    @GetMapping("/userregform")
    public String userregForm() {
        return "users/userregform";
    }

    @PostMapping("/userregform")
    public String signUp(
            @ModelAttribute User user, BindingResult result,
            Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("errorMessage", "다시 입력해주세요.");
            return "error";
        }

        MultipartFile profileImageFile = user.getProfileImageFile();
        if (profileImageFile != null && !profileImageFile.isEmpty()) {
            String filename = UUID.randomUUID().toString() + "_" + profileImageFile.getOriginalFilename();
            Path imagePath = Paths.get("src/main/resources/static/images/" + filename);
            Files.createDirectories(imagePath.getParent());
            Files.write(imagePath, profileImageFile.getBytes());
            user.setProfileImage(filename);
        } else {
            user.setProfileImage("bono.png");
        }

        model.addAttribute("userRegMessage", "Welcome " + user.getUsername());
        userService.createUser(user, passwordEncoder);

        return "redirect:/velog";
    }

    @GetMapping("/{username}/settings")
    public String showSettings(@PathVariable String username, Model model, Principal principal) {
        if (username.equals(principal.getName())) {
            User user = userService.findByUsername(principal.getName());
            model.addAttribute("user", user);
        }
        return "users/setting";
    }

    @PutMapping("/{username}/settings/update")
    public String editUserInfo(@PathVariable String username, @ModelAttribute User updateUser, Principal principal) {
        if (username.equals(principal.getName())) {
            User user = userService.findByUsername(principal.getName());
            userService.updateUser(user, updateUser);
        }
        return "users/setting";
    }

    @DeleteMapping("/{username}/settings/delete")
    public String deleteUser(@PathVariable String username, @RequestParam("password") String password) {
        User user = userService.findByUsername(username);
        if (passwordEncoder.matches(password, user.getPassword())) {
            userService.deleteUser(user);
            log.info("User {} deleted", user.getUsername());
        }
        return "redirect:/logout";
    }

    @GetMapping("/{username}/drafts")
    public String showUserBlogDrafts(@PathVariable String username, Model model) {
        User loginUser = userService.findByUsername(username);
        model.addAttribute("loginUser", loginUser);
        List<Post> allPosts = postService.findAllByUserOrderByReleaseDateDesc(loginUser);
        List<Post> archivedPosts = new ArrayList<>();

        for (Post post : allPosts) {
            if ("ARCHIVED".equals(post.getPostStatus().toString())) {
                archivedPosts.add(post);
            }
        }
        model.addAttribute("posts", archivedPosts);
        return "draft";
    }
}
