package com.patikadev.finalcase.security;

import com.patikadev.finalcase.service.impl.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class JwtRequestFilterTest {

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtRequestFilter jwtRequestFilter;

    private String token;
    private String username;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        username = "testuser";
        token = "Bearer valid_token";
        userDetails = mock(UserDetails.class);
    }

    @Test
    void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.validateToken(anyString())).thenReturn(true);
        when(jwtUtil.extractUsername(anyString())).thenReturn(username);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn(username);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, times(1)).validateToken(anyString());
        verify(jwtUtil, times(1)).extractUsername(anyString());
        verify(userDetailsService, times(1)).loadUserByUsername(anyString());
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(username, ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    @Test
    void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(token);
        when(jwtUtil.validateToken(anyString())).thenReturn(false);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, times(1)).validateToken(anyString());
        verify(jwtUtil, times(0)).extractUsername(anyString());
        verify(userDetailsService, times(0)).loadUserByUsername(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_NoToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn(null);

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, times(0)).validateToken(anyString());
        verify(jwtUtil, times(0)).extractUsername(anyString());
        verify(userDetailsService, times(0)).loadUserByUsername(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_EmptyToken() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("");

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, times(0)).validateToken(anyString());
        verify(jwtUtil, times(0)).extractUsername(anyString());
        verify(userDetailsService, times(0)).loadUserByUsername(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }

    @Test
    void testDoFilterInternal_TokenWithoutBearer() throws ServletException, IOException {
        when(request.getHeader("Authorization")).thenReturn("invalid_token");

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(jwtUtil, times(0)).validateToken(anyString());
        verify(jwtUtil, times(0)).extractUsername(anyString());
        verify(userDetailsService, times(0)).loadUserByUsername(anyString());
        assertNull(SecurityContextHolder.getContext().getAuthentication());
    }
}