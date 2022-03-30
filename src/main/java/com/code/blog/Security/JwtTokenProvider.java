package com.code.blog.Security;

import com.code.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private String jwtExpirationInMs;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date expiryDate = new Date(System.currentTimeMillis() + Long.parseLong(jwtExpirationInMs));

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        return token;
    }

    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            throw new BlogAPIException("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            throw new BlogAPIException("Invalid JWT token");
        }catch (ExpiredJwtException ex){
            throw new BlogAPIException("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            throw new BlogAPIException("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            throw new BlogAPIException("JWT claims string is empty");
        }
    }
}
