package com.omori.chatapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Value("${websocket.allowed-origins}")
  private String allowedOrigins;

  @Value("${websocket.endpoint")
  private String endpoint;

  @Value("${websocket.destination-frefix}")
  private String destinationPrefix;

  @Value("${websocket.broker-prefix}")
  private String brokerFrefix;

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint(endpoint)
        .setAllowedOrigins(allowedOrigins.split(","))
        .withSockJS();
  }

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.setApplicationDestinationPrefixes(destinationPrefix);
    registry.enableSimpleBroker(brokerFrefix);

    // use redis message broker for production
    // registry.enableStompBrokerRelay("/topic", "/queue")
    // .setRelayHost("localhost")
    // .setRelayPort(61613)
    // .setClientLogin("guest")
    // .setClientPasscode("guest");

  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(webSocketAuthInterceptor());
  }

  public WebSocketAuthInterceptor webSocketAuthInterceptor() {
    return new WebSocketAuthInterceptor();
  }

}
