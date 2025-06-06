package com.omori.chatapp.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityLoggerAspect {

  private static final Logger logger = LoggerFactory.getLogger(SecurityLoggerAspect.class);

  @Before("@annotation(org.springframework.security.access.prepost.PreAuthorize)")
  public void logSecurityAttempt(JoinPoint joinPoint) {
    String method = joinPoint.getSignature().toShortString();
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    String user = auth != null ? auth.getName() : "anonymous";
    logger.info("[SECURITY] Attempt by user'{}' to access '{}' ", user, method);

  }

  @AfterThrowing(pointcut = "(@annotation(org.springframework.security.access.prepost.PreAuthorize)", throwing = "ex")

  public void logSecurityFailure(JoinPoint joinPoint, Throwable ex) {
    logger.error("[SECURITY] Access denined: {}", ex.getMessage());
  }
}
