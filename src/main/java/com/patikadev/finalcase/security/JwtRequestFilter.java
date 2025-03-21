package com.patikadev.finalcase.security;

import java.io.IOException;

import com.patikadev.finalcase.security.JwtUtil;
import com.patikadev.finalcase.service.impl.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil; // JWT doğrulama ve token çıkarma işlemleri için yardımcı sınıf

    private final CustomUserDetailsService userDetailsService; // Kullanıcı bilgilerini getiren servis


    public JwtRequestFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = (CustomUserDetailsService) userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String token = getJwtFromRequest(request); // Header'dan token'ı al
        if (token != null && jwtUtil.validateToken(token)) { // Token geçerli mi?
            String username = jwtUtil.extractUsername(token); // Token'dan kullanıcı adını çıkar
            UserDetails userDetails = userDetailsService.loadUserByUsername(username); // Kullanıcı bilgilerini getir

            // Kullanıcı bilgileriyle kimlik doğrulama nesnesi oluştur
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Kullanıcıyı doğrulanmış olarak işaretle
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request, response); // İsteği devam ettir
    }

    // Authorization header'ından JWT token'ı çıkaran yardımcı metot
    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " kelimesinden sonrasını al
        }
        return null;
    }
}
