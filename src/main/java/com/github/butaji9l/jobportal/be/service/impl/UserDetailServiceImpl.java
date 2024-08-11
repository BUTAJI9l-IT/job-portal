package com.github.butaji9l.jobportal.be.service.impl;

import com.github.butaji9l.jobportal.be.annotation.JobPortalService;
import com.github.butaji9l.jobportal.be.exception.EntityNotFoundException;
import com.github.butaji9l.jobportal.be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link UserDetailsService} Implementation
 *
 * @author Vitalii Bortsov
 */
@JobPortalService
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
      .map(user -> User.withUsername(email).password(user.getPassword())
        .authorities(new SimpleGrantedAuthority(user.getScope().toString())).build())
      .orElseThrow(() -> {
        throw new EntityNotFoundException(com.github.butaji9l.jobportal.be.domain.User.class);
      });
  }
}
