package com.omori.chatapp.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import com.omori.chatapp.service.impl.UserDetailsServiceImpl;
import com.omori.chatapp.utils.JwtUtils;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

  private final JwtUtils jwtUtils;
  private final UserDetailsServiceImpl userDetailsServiceImpl;

  public JwtChannelInterceptor(JwtUtils jwtUtils, UserDetailsServiceImpl userDetailsServiceImpl) {
    this.jwtUtils = jwtUtils;
    this.userDetailsServiceImpl = userDetailsServiceImpl;
  }

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
    if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
      String token = accessor.getFirstNativeHeader("Authorization");
      if (token != null && token.startsWith("Bearer ")) {
        String username = jwtUtils.extractUsername(token.substring(7));
        String jwt = token.substring(7);
        var userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        if (jwtUtils.validateToken(jwt, userDetails)) {
          var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
          accessor.setUser(auth);
        }
      }
    }
    return message;
  }
}
