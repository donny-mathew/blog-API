package com.code.blog.Security;

import com.code.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private String jwtExpirationInMs;

    private SecretKey getSignInKey() {
        // If the secret is Base64 encoded
        // return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        // If it's just a raw string, we might need to ensure it's long enough or bytes
        // For simplicity reusing the string bytes if it's simple, but best practice is
        // Base64.
        // Given previous implementation usage, it probably was treated as raw bytes or
        // string.
        // JJWT 0.12+ enforces strong keys.
        // If jwtSecret is too short, this might fail.
        // Assuming jwtSecret is a base64 encoded string or a long string.
        // If not, we might need to fix the property or padding.
        // Let's try standard HMAC key generation
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + Long.parseLong(jwtExpirationInMs));

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException ex) {
            throw new BlogAPIException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new BlogAPIException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new BlogAPIException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new BlogAPIException("JWT claims string is empty");
        }
    }
}
