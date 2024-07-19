package org.example.blogproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.blogproject.domain.SocialLoginInfo;
import org.example.blogproject.service.SocialLoginInfoService;
import org.example.blogproject.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class Oauth2Controller {
    private final UserService userService;
    private final SocialLoginInfoService socialLoginInfoService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/registersocialuser")
    public String registerSocialUser(@RequestParam("provider")String provider, @RequestParam("socialId")String socialId,
                                     @RequestParam("uuid") String uuid, @RequestParam("name")String name, Model model){
        model.addAttribute("provider",provider);
        model.addAttribute("socialId",socialId);
        model.addAttribute("name", name);
        model.addAttribute("uuid",uuid);
        return "users/registersocialuser";
    }

    @PostMapping("/saveSocialUser")
    public String saveSocialUser(@RequestParam("provider")  String provider, @RequestParam("socialId")
    String socialId, @RequestParam("name")  String name, @RequestParam("username")  String username, @RequestParam("email")
                                 String email, @RequestParam("uuid")  String uuid, Model model) {
        Optional<SocialLoginInfo> socialLoginInfoOptional = socialLoginInfoService.findByProviderAndUuidAndSocialId(provider, uuid, socialId);

        if (socialLoginInfoOptional.isPresent()) {
            SocialLoginInfo socialLoginInfo = socialLoginInfoOptional.get();
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(socialLoginInfo.getCreatedAt(), now);

            if (duration.toMinutes() > 20) {
                return "redirect:/error";
            }

            userService.saveUser(username, name, email, socialId, provider,passwordEncoder);
            return "redirect:/velog/loginform";
        } else {
            return "redirect:/error";
        }
    }
}