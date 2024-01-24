package com.quiet.onterview.security.jwt;

import com.quiet.onterview.common.BaseException;
import com.quiet.onterview.common.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Duration;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    public enum TokenType {
        Access, Refresh
    };

    @Value("${jwt.secret}")
    private String secretKey;

    public String generateToken(TokenType tokenType, Long userId) {
        Date now = new Date();
        int duration = tokenType.equals(TokenType.Access) ? 1 : 21;
        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofDays(duration).toMillis()));
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwt;
    }

    public String generateAccessToken(String email) {
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofSeconds(1).toMillis()));
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwt;
    }

    public String generateRefreshToken(String email) {
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Duration.ofSeconds(10000).toMillis()));
        String jwt = Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        return jwt;
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            return !claims.getExpiration().before(new Date());
        } catch(ExpiredJwtException expiredJwtException) {
            return false;
        }
    }

    public Long getUserId(String accessToken) {
        if(!isValidToken(accessToken)) {
            throw new BaseException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        String userId = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
        return Long.parseLong(userId);
    }

    public String getEmail(String accessToken) {
        if (!isValidToken(accessToken)) {
            throw new BaseException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        }
        String email = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody().getSubject();
        return email;
    }
}