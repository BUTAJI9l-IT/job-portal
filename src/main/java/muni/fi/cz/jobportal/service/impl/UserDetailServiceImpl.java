package muni.fi.cz.jobportal.service.impl;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

  private final UserRepository userRepository;

  @Transactional(readOnly = true)
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
      .map(user -> User.withUsername(email).password(user.getPassword())
        .authorities(new SimpleGrantedAuthority(user.getScope().toString())).build())
      .orElseThrow(() -> {
        throw new RuntimeException("User not found");
      });
  }
}
