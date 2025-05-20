package com.omori.chatapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final AuthenticationProvider authenticationProvider;
  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  @Autowired
  public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
      AuthenticationProvider authenticationProvider) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.authenticationProvider = authenticationProvider;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // turn off the CSRF to test API easy (just in dev environment)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/api/auth/**",
                "/swagger-ui/**", // pretty UI
                "/v3/api-docs/**") // Raw JSON
            .permitAll()
            .requestMatchers("/api/users/{userId}/profile").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/users/**/password").authenticated()
            .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")
            .anyRequest().authenticated() // other request must authenticate
        )
        .formLogin(login -> login
            .successHandler((request, response, auth) -> {
              logger.info("[AUTH] Login Success: {}", auth.getName());
            })
            .failureHandler((request, response, ex) -> {
              logger.warn("[AUTH] Login failed: {}", ex.getMessage());
            }))
        .exceptionHandling(handling -> handling
            .accessDeniedHandler((request, response, ex) -> {
              logger.warn("[AUTH] Ascess Denied for {} : {}", request.getRequestURI(), ex.getMessage());
              response.sendError(HttpStatus.FORBIDDEN.value(), "Access denied");
            }))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider)
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
      throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

}
