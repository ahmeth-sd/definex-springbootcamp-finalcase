package com.patikadev.finalcase.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JwtUtilTest {

    @InjectMocks
    private JwtUtil jwtUtil;

    private String token;
    private String username;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        username = "testuser";
        token = jwtUtil.generateToken(username);
    }

    @Test
    void testGenerateToken() {
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testExtractUsername() {
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void testIsTokenExpired() {
        assertFalse(jwtUtil.isTokenExpired(token));
    }

    @Test
    void testValidateToken() {
        assertTrue(jwtUtil.validateToken(token));
    }

    @Test
    void testExtractExpiration() {
        Date expiration = jwtUtil.extractExpiration(token);
        assertNotNull(expiration);
        assertTrue(expiration.after(new Date()));
    }

    @Test
    void testExtractClaim() {
        try (MockedStatic<Jwts> jwtsMockedStatic = mockStatic(Jwts.class)) {
            JwtParser jwtParser = mock(JwtParser.class);
            Claims claims = mock(Claims.class);
            Jws<Claims> jwsClaims = mock(Jws.class);

            when(claims.getSubject()).thenReturn(username);
            when(jwsClaims.getBody()).thenReturn(claims);
            when(jwtParser.setSigningKey(anyString())).thenReturn(jwtParser);
            when(jwtParser.parseClaimsJws(anyString())).thenReturn(jwsClaims);
            jwtsMockedStatic.when(Jwts::parser).thenReturn(jwtParser);

            String extractedUsername = jwtUtil.extractClaim(token, Claims::getSubject);
            assertEquals(username, extractedUsername);
        }
    }
}