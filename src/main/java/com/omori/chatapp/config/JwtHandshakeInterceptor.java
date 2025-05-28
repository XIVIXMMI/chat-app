package com.omori.chatapp.config;

import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.omori.chatapp.service.impl.UserDetailsServiceImpl;
import com.omori.chatapp.utils.JwtUtils;

@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

  private final JwtUtils jwtUtils;
  private final UserDetailsServiceImpl userDetailsService;

  public JwtHandshakeInterceptor(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsService) {
    this.jwtUtils = jwtUtils;
    this.userDetailsService = userDetailsService;
  }

  @Override
  public boolean beforeHandshake(ServerHttpRequest request,
      ServerHttpResponse response,
      WebSocketHandler wsHandler,
      Map<String, Object> attributes) {
    if (request instanceof ServletServerHttpRequest servletRequest) {
      String token = servletRequest.getServletRequest().getHeader("Authorization");
      if (token != null && token.startsWith("Bearer ")) {
        String jwt = token.substring(7);
        try {
          String username = jwtUtils.extractUsername(jwt);
          UserDetails userDetails = userDetailsService.loadUserByUsername(username);

          if (!jwtUtils.validateToken(jwt, userDetails)) {
            return false; // Reject connection if token is invalid
          }

          attributes.put("jwt", jwt); // Store token for session
          return true;
        } catch (Exception e) {
          return false; // Reject on any error (invalid token, user not found, etc.)
        }
      }
    }
    return false; // Reject if no valid token provided
  }

  @Override
  public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
      WebSocketHandler wsHandler, Exception ex) {
    // Optional: bạn có thể log token ở đây nếu cần
  }
}
