package com.omori.chatapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

  @Value("${security.jwt.secret}")
  private String secretKey;

  @Value("${security.jwt.expiration-time}")
  private long expirationMs;

  // Tạo JWT từ username
  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
        .signWith(SignatureAlgorithm.HS256, secretKey)
        .compact();
  }

  // Giải mã token để lấy thông tin
  public Claims extractAllClaims(String token) {
    try {
      return Jwts.parser()
          .setSigningKey(secretKey)
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      throw new RuntimeException("Token đã hết hạn", e);
    } catch (SignatureException e) {
      throw new RuntimeException("Chữ ký không hợp lệ", e);
    }
  }

  // Lấy username từ token
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // Kiểm tra token còn hợp lệ không
  public boolean isTokenValid(String token, String username) {
    final String extractedUsername = extractUsername(token);
    return extractedUsername.equals(username) && !isTokenExpired(token);
  }

  // Kiểm tra token hết hạn chưa
  public boolean isTokenExpired(String token) {
    Date expiration = extractClaim(token, Claims::getExpiration);
    return expiration.before(new Date());
  }

  // Helper để trích xuất 1 claim bất kỳ
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }
}
