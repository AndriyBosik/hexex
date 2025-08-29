package org.example.hexex.adapter.out.security.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public record SecurityUser(
  Long id,
  String email,
  String password,
  String firstName,
  String lastName,
  String... authorities
) implements UserDetails {
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Arrays.stream(authorities)
      .map("ROLE_%s"::formatted)
      .map(SimpleGrantedAuthority::new)
      .toList();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }
}
