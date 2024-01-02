package com.creolophus.lucky.common.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author magicnana
 * @date 2021/10/25 20:37
 */
public abstract class TokenParser implements UserDetailsService {

  protected abstract UserDetails parseToken(String token);

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return parseToken(username);
  }
}
