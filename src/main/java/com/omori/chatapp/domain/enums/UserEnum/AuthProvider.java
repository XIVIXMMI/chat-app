package com.omori.chatapp.domain.enums.UserEnum;

public enum AuthProvider {
  LOCAL, GOOGLE, FACEBOOK, GITHUB;
  // public static AuthProvider froVProvidermString(String value) {
  // for (AuthProvider authProvider : AuthProvider.values()) {
  // if (authProvider.name().equalsIgnoreCase(value)) {
  // return authProvider;
  // }
  // }
  // throw new IllegalArgumentException("No enum constant for value: " + value);
  // }
}
