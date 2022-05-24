package com.yj.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY_MILLISECONDS = 5 * 60 * 60 * 1000L;

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    public String generateToken(String userId) {
        // 토큰 발행 시간 및 만료 시간
        Date issuedTime = new Date();
        Date expiredDate = new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_MILLISECONDS);

        // 토큰 생성
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(userId)
                .setIssuer("Todo App")
                .setIssuedAt(issuedTime)
                .setExpiration(expiredDate)
                .compact();
    }

    public String validateAndGetUserId(String token) {
        // Base64 디코딩 후, 서명 알고리즘을 이용하여 토큰 유효성 확인
        // 유효하지 않은 경우 예외 발생
        // 유효한 경우 유저의 아이디 반환
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
