package com.omori.chatapp.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.omori.chatapp.service.impl.UserDetailsServiceImpl;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsServiceImpl userDetailsServiceImpl;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public CustomAuthenticationProvider(UserDetailsServiceImpl userDetailsServiceImpl, PasswordEncoder passwordEncoder) {
    this.userDetailsServiceImpl = userDetailsServiceImpl;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    UserDetails user = userDetailsServiceImpl.loadUserByUsername(username);
    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new BadCredentialsException("Invalid credenitals");
    }
    return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

}
