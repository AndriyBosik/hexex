package org.example.hexex.adapter.out.security;

import lombok.RequiredArgsConstructor;
import org.example.hexex.domain.model.UserIdentity;
import org.example.hexex.application.port.out.LoadCurrentUserPort;
import org.example.hexex.common.annotation.Adapter;
import org.example.hexex.common.exception.ForbiddenException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

@Adapter
@RequiredArgsConstructor
public class CurrentUserAdapter implements LoadCurrentUserPort {
  @Override
  public UserIdentity transfer() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !authentication.isAuthenticated()) {
      throw new BadCredentialsException("User is not authenticated");
    }
    if (authentication.getPrincipal() instanceof User user) {
      return new UserIdentity(user.getUsername());
    }
    throw new ForbiddenException("User is not authorized");
  }
}
