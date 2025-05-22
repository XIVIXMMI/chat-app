package com.omori.chatapp.listener;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.omori.chatapp.service.UserActivityService;

import lombok.RequiredArgsConstructor;

@Component
public class PresenceEventListener {

  private final UserActivityService userActivityService;

  public PresenceEventListener(UserActivityService userActivityService) {
    this.userActivityService = userActivityService;
  }

  @EventListener
  public void handleWebSocketConnection(SessionConnectedEvent event) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = accessor.getUser().getName();
    userActivityService.markOffline(username);
  }

  @EventListener
  public void handleWebsocketDisconnect(SessionDisconnectEvent event) {
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
    String username = accessor.getUser().getName();
    userActivityService.markOffline(username);
  }

}
