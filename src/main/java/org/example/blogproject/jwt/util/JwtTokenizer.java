package org.example.blogproject.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtTokenizer {
    private final byte[] accessSecret;
    private final byte[] refreshSecret;

    public static Long ACCESS_TOKEN_EXPIRE_COUNT = 30 * 60 * 1000L; //30분
    public static Long REFRESH_TOKEN_EXPIRE_COUNT=7*24*60*60*1000L; //7일

    public JwtTokenizer(@Value("${jwt.secretKey}") String accessSecret, @Value("${jwt.refreshKey}") String refreshSecret){
        this.accessSecret = accessSecret.getBytes(StandardCharsets.UTF_8);
        this.refreshSecret = refreshSecret.getBytes(StandardCharsets.UTF_8);
    }

    private String createToken(Long id, String email, String name, String username,
                               List<String> roles, Long expire, byte[] secretKey){

        Claims claims = Jwts.claims().setSubject(email);

        //필요한 정보들을 저장한다.
        claims.put("username",username);
        claims.put("name",name);
        claims.put("userId",id);
        claims.put("roles", roles);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expire))
                .signWith(getSigningKey(secretKey))
                .compact();

    }

    //ACCESS Token 생성
    public String createAccessToken(Long id, String email, String name, String username, List<String> roles){
        return createToken(id,email,name,username,roles,ACCESS_TOKEN_EXPIRE_COUNT,accessSecret);
    }
    //Refresh Token생성
    public String createRefreshToken(Long id, String email, String name, String username, List<String> roles){
        return createToken(id,email,name,username,roles,REFRESH_TOKEN_EXPIRE_COUNT,refreshSecret);
    }

    public static Key getSigningKey(byte[] secretKey){
        return Keys.hmacShaKeyFor(secretKey);
    }

    public Long getUserIdFromToken(String token){
        String[] tokenArr = token.split(" ");
        token = tokenArr[1];
        Claims claims = parseToken(token,accessSecret);
        return Long.valueOf((Integer)claims.get("userId"));
    }

    public Claims parseToken(String token, byte[] secretKey){
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public Claims parseAccessToken(String accessToken) {
        return parseToken(accessToken, accessSecret);
    }

    public Claims parseRefreshToken(String refreshToken) {
        return parseToken(refreshToken, refreshSecret);
    }

    /**
     * JWT 액세스 토큰의 유효성을 검증합니다.
     *
     * @param token JWT 액세스 토큰
     * @return 유효한 토큰이면 true, 그렇지 않으면 false
     */
    public boolean validateAccessToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(accessSecret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey(refreshSecret))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}