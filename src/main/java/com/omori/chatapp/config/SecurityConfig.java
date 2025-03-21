package com.omori.chatapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig
 */
@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // turn off the CSRF to test API easy (just in dev environment)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/api/auth/register", "/api/auth/login").permitAll() // allow API signup and login
            .anyRequest().authenticated() // orther request must authenticated
        )
        // no need to use session-based authentication.
        // .sessionManagement(session ->
        // session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        // add the config authentication to testing
        .httpBasic(Customizer.withDefaults());
    return http.build();
  }

  // Default user to testing
  @Bean
  public InMemoryUserDetailsManager userDetailsManager() {
    UserDetails user = User.withUsername("admin")
        .password("password") // {noop} for not using hash password
        .roles("ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user);
  }

}
