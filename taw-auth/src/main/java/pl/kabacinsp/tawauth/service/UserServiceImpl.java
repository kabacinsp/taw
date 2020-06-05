package pl.kabacinsp.tawauth.service;

import pl.kabacinsp.tawauth.model.User;
import pl.kabacinsp.tawauth.enums.Authorities;
import pl.kabacinsp.tawauth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final PasswordEncoder passwordEncoder;
  private UserRepository userRepository;

  public UserServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  @Override
  public User create(User user) {
    throwIfUsernameExists(user.getUsername());

    String hash = passwordEncoder.encode(user.getPassword());
    user.setPassword(hash);
    user.setActivated(Boolean.TRUE); // TODO send sms or email with code for activation
    user.setAuthorities(new HashSet<Authorities>(Collections.singletonList(Authorities.ROLE_USER)));

    // TODO other routines on account creation

    return userRepository.save(user);
  }

  @Override
  public User update(User user) {
    return userRepository.save(user);
  }

  @Override
  public User delete(User user) {
    userRepository.delete(user);
    return user;
  }

  private void throwIfUsernameExists(String username) {
    Optional<User> existingUser = userRepository.findByUsername(username);
    existingUser.ifPresent(
        user -> {
          throw new IllegalArgumentException("User not available");
        });
  }
}
