package pl.kabacinsp.tawauth.service;

import pl.kabacinsp.tawauth.model.UserClient;
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
  public UserClient create(UserClient userClient) {
    throwIfUsernameExists(userClient.getUsername());

    String hash = passwordEncoder.encode(userClient.getPassword());
    userClient.setPassword(hash);
    userClient.setActivated(Boolean.TRUE); // TODO send sms or email with code for activation
    userClient.setAuthorities(new HashSet<Authorities>(Collections.singletonList(Authorities.ROLE_USER)));

    // TODO other routines on account creation

    return userRepository.save(userClient);
  }

  @Override
  public UserClient update(UserClient userClient) {
    return userRepository.save(userClient);
  }

  @Override
  public UserClient delete(UserClient userClient) {
    userRepository.delete(userClient);
    return userClient;
  }

  private void throwIfUsernameExists(String username) {
    Optional<UserClient> existingUser = userRepository.findByUsername(username);
    existingUser.ifPresent(
        user -> {
          throw new IllegalArgumentException("User not available");
        });
  }
}
