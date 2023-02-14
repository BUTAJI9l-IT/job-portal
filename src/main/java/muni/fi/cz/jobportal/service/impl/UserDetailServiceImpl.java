package muni.fi.cz.jobportal.service.impl;

import lombok.RequiredArgsConstructor;
import muni.fi.cz.jobportal.annotation.JobPortalService;
import muni.fi.cz.jobportal.exception.EntityNotFoundException;
import muni.fi.cz.jobportal.repository.UserRepository;
import org.springframework.lang.NonNull;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

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
        throw new EntityNotFoundException(muni.fi.cz.jobportal.domain.User.class);
      });
  }
}
