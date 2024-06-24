package org.example.blogproject.controller;

import org.example.blogproject.domain.User;
import org.example.blogproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/loginform")
    public String loginForm() {
        return "loginform";
    }

    @GetMapping("/userregform")
    public ModelAndView userRegForm() {
        ModelAndView modelAndView = new ModelAndView("userregform");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }

    @PostMapping("/userreg")
    public ModelAndView registerUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView("userregform");

        Optional<User> existingUserByUsername = userService.findByUsername(user.getUsername());
        Optional<User> existingUserByEmail = userService.findByEmail(user.getEmail());

        if (existingUserByUsername.isPresent() || existingUserByEmail.isPresent()) {
            bindingResult.rejectValue("username", "error.user", "Username or email is already taken");
            return modelAndView;
        }

        if (bindingResult.hasErrors()) {
            return modelAndView;
        }

        userService.registerNewUser(user);
        modelAndView.setViewName("userreg");
        return modelAndView;
    }

    @GetMapping("/userreg")
    public String userReg() {
        return "userreg";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
