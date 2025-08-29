package org.example.hexex.configuration;

import org.example.hexex.adapter.out.security.model.SecurityUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class Security {
  @Bean
  SecurityFilterChain chain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(requests -> requests
        .requestMatchers(HttpMethod.POST, "/api/deposit/instant").hasAnyAuthority("ROLE_USER")
        .requestMatchers(HttpMethod.PUT, "/api/deposit").hasAuthority("ADMIN"))
      .formLogin(Customizer.withDefaults())
      .httpBasic(Customizer.withDefaults())
      .cors(AbstractHttpConfigurer::disable)
      .csrf(AbstractHttpConfigurer::disable);

    return http.build();
  }

  @Bean
  UserDetailsManager inMemoryService() {
    return new InMemoryUserDetailsManager(
      new SecurityUser(
        1L,
        "admin@yopmail.com",
        "{noop}adminpass",
        "Admin",
        "Admin",
        "ADMIN"),
      new SecurityUser(
        2L,
        "user@yopmail.com",
        "{noop}userpass",
        "Andrii",
        "Bosyk",
        "USER"));
  }
}
