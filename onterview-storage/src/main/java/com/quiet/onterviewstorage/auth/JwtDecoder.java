package com.quiet.onterviewstorage.auth;

import io.jsonwebtoken.*;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtDecoder {

    @Value("${jwt.secret}")
    private String secretKey;

    private Jws<Claims> getClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException e) {
            throw new JwtException("token is not trusted");
        } catch (MalformedJwtException e) {
            throw new JwtException("token is malformed");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("token is unsupported");
        } catch (ExpiredJwtException e) {
            throw new JwtException("token is expired");
        } catch (IllegalArgumentException e) {
            throw new JwtException("token is empty");
        }
    }

    public boolean validateToken(String token) {
        return getClaims(token)
                .getBody()
                .getExpiration()
                .after(new Date());
    }
}