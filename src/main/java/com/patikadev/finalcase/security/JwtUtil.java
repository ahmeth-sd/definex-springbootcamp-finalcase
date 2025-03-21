package com.patikadev.finalcase.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "your_secret_key"; // Güçlü bir secret key kullan!

    // Kullanıcı adı ile JWT oluştur
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 saat geçerli
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Token'dan kullanıcı adını çıkar
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Token süresi dolmuş mu?
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Token geçerli mi?
    public boolean validateToken(String token) {
        return (extractUsername(token) != null && !isTokenExpired(token));
    }

    // Token'dan expiration bilgisini al
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Token'dan bir claim bilgisi çek
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}
