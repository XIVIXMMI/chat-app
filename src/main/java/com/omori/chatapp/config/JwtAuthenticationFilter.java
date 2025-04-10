package com.omori.chatapp.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.omori.chatapp.service.impl.UserDetailsServiceImpl;
import com.omori.chatapp.utils.JwtUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;
  private final UserDetailsServiceImpl userDetailsSeviceImpl;

  @Autowired
  public JwtAuthenticationFilter(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsSeviceImpl) {
    this.jwtUtils = jwtUtils;
    this.userDetailsSeviceImpl = userDetailsSeviceImpl;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    try {
      String token = extractToken(request);
      if (token != null) {
        logger.info("Token received: " + token);
        String username = jwtUtils.extractUsername(token);
        logger.info("Extracted username: " + username);
        UserDetails userDetails = userDetailsSeviceImpl.loadUserByUsername(username);

        if (jwtUtils.validateToken(token, userDetails)) {

          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          // set authentication details
          logger.info("User authorities: " + userDetails.getAuthorities());
          authenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
      chain.doFilter(request, response);
    } catch (Exception e) {
      // log error , handle token validation exception
      logger.error("JWT Authentication Error: ", e);
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("Authentication failed: " + e.getMessage());
    }
  }

  private String extractToken(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }
    return null;
  }
}
