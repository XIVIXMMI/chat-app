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

import com.omori.chatapp.service.UserDetailsSeviceImpl;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private final UserDetailsSeviceImpl userDetailsSeviceImpl;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public CustomAuthenticationProvider(UserDetailsSeviceImpl userDetailsSeviceImpl, PasswordEncoder passwordEncoder) {
    this.userDetailsSeviceImpl = userDetailsSeviceImpl;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    UserDetails user = userDetailsSeviceImpl.loadUserByUsername(username);
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
