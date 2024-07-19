package org.example.blogproject.config;

import lombok.RequiredArgsConstructor;
import org.example.blogproject.jwt.filter.JwtAuthenticationFilter;
import org.example.blogproject.jwt.util.JwtTokenizer;
import org.example.blogproject.security.CustomAuthenticationSuccessHandler;
import org.example.blogproject.security.CustomOAuth2AuthenticationSuccessHandler;
import org.example.blogproject.service.SocialUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomOAuth2AuthenticationSuccessHandler customOAuth2AuthenticationSuccessHandler;
    private final SocialUserService socialUserService;
    private final CustomAuthenticationSuccessHandler successHandler;
    private final JwtTokenizer jwtTokenizer;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtTokenizer);

        http
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/", "/*", "/velog").permitAll()
                                .requestMatchers("/velog/loginform", "/velog/userregform").permitAll()
                                .requestMatchers("/oauth2/**", "/login/oauth2/code/github", "/registerSocialUser", "/saveSocialUser").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // 정적 자원 허용
                                .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .formLogin(form -> form
                        .loginPage("/velog/loginform")
                        .successHandler(successHandler)
                        .failureUrl("/velog/login?error=true")
                        .loginProcessingUrl("/velog/login")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/velog")
                        .invalidateHttpSession(true)
                        .deleteCookies("JESSIONID", "accessToken", "refreshToken", "username")
                        .permitAll()
                )
                .cors(cors -> cors.configurationSource(configurationSource()))
                .httpBasic(httpBasic -> httpBasic.disable())
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/velog/loginform")
                        .failureUrl("/velog")
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(this.oauth2UserService())
                        )
                        .successHandler(customOAuth2AuthenticationSuccessHandler)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
        return oauth2UserRequest -> {
            OAuth2User oauth2User = delegate.loadUser(oauth2UserRequest);

            String token = oauth2UserRequest.getAccessToken().getTokenValue();

            String provider = oauth2UserRequest.getClientRegistration().getRegistrationId();
            String socialId = String.valueOf(oauth2User.getAttributes().get("id"));
            String username = (String) oauth2User.getAttributes().get("login");
            String email = (String) oauth2User.getAttributes().get("email");
            String avatarUrl = (String) oauth2User.getAttributes().get("avatar_url");

            socialUserService.saveOrUpdateUser(socialId, provider, username, email, avatarUrl);

            return oauth2User;
        };
    }


    public CorsConfigurationSource configurationSource(){
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        config.setAllowedMethods(List.of("GET","POST","DELETE"));
        source.registerCorsConfiguration("/**",config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}