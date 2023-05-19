package com.matteo.ToDo_app.Security;

import com.matteo.ToDo_app.Services.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userService;

    public JwtRequestFilter(JwtUtils jwtUtils, UserServiceImpl userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (httpServletRequest.getServletPath().contains("/v1/auth")){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        final String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String jwt;
        String userEmail;
        String userId;
        if(authorizationHeader == null) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        if (authorizationHeader.startsWith("Bearer "))
            jwt = authorizationHeader.substring(7);
        else
            jwt = authorizationHeader;
        userEmail = jwtUtils.extractUserName(jwt);
        userId = jwtUtils.extractUserID(jwt);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userService.loadUserByUsername(userEmail);
            if(jwtUtils.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken  = new UsernamePasswordAuthenticationToken(userDetails,userId, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
