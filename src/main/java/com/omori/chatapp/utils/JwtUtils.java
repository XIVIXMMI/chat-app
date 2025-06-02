package com.omori.chatapp.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.omori.chatapp.config.EnvLoader;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtils {

  private final EnvLoader envLoader;
  private SecretKey secretKey;
  private String secretKeyString;
  private long expirationTime;

  public JwtUtils(EnvLoader envLoader) {
    this.envLoader = envLoader;
  }

  @PostConstruct
  public void init() {
    this.secretKeyString = envLoader.get("JWT_SECRET");
    this.expirationTime = Long.parseLong(envLoader.get("JWT_EXPIRATION"));
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));
  }

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(secretKey)
        .compact();
  }

  public String extractUsername(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public boolean isTokenExpired(String token) {
    return Jwts.parserBuilder()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration()
        .before(new Date());
  }

}
