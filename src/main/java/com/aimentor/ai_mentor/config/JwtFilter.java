package com.aimentor.ai_mentor.config;

import java.io.IOException;
import java.util.Collections;

import com.aimentor.ai_mentor.user.User;
import com.aimentor.ai_mentor.user.UserRepository;
import com.aimentor.ai_mentor.user.exception.InvalidCredentialsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {
        String token = delereStroke(request);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }
        boolean validToken = jwtUtils.validateJwtToken(token);
        if (!validToken){
            filterChain.doFilter(request,response);
            return;
        }
        String email = jwtUtils.getUsernameFromJwtToken(token);
        User user = userRepository.findByEmail(email).
                orElseThrow(()-> new InvalidCredentialsException("invalid credentials exception"));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user,
                null,
                Collections.emptyList()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);


        filterChain.doFilter(request, response);
    }

    private String delereStroke(HttpServletRequest message){
            String authHeader = message.getHeader("Authorization");
            if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
                return authHeader.substring(7);
            }
            return authHeader;
        }
    }

