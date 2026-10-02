package com.aimentor.ai_mentor.config;


import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.secret}")
    private String key;

    private Key securityKey(){return Keys.hmacShaKeyFor(key.getBytes());};
//generate token
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpiration))
                .signWith(securityKey(), SignatureAlgorithm.HS256)
                .compact();
    }
//username from token
    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .setSigningKey(securityKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
//validation token
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser()
                    .setSigningKey(securityKey())
                    .build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            System.err.println("Недействительная подпись JWT: " + e.getMessage());
        } catch (MalformedJwtException e) {
            System.err.println("Недействительный токен JWT: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("Срок действия токена JWT истек: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("Токен JWT не поддерживается: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Cтрока claims JWT пуста: " + e.getMessage());
        }

        return false;
    }

}
