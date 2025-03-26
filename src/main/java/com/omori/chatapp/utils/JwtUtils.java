package com.omori.chatapp.utils;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

/**
 * JwtUtils
 */
@Component
public class JwtUtils {

  @Value("${jwt.secret}")
  private String secretKeyString;

  @Value("${jwt.expiration-time}")
  private long expirationTime;

  private SecretKey secretKey;

  // public JwtUtils(
  // @Value("${jwt.secret}") String secretKeyString,
  // @Value("${jwt.expiration-time}") long expirationTime) {
  // this.secretKeyString = secretKeyString;
  // this.expirationTime = expirationTime;
  // }

  @PostConstruct
  public void init() {
    this.secretKey = Keys.hmacShaKeyFor(
        Decoders.BASE64.decode(secretKeyString));
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
