package com.omori.chatapp.service;

import com.omori.chatapp.dto.RegisterRequestDTO;

/**
 * AuthService
 */
public interface AuthService {

  void registerUser(RegisterRequestDTO request);
}
