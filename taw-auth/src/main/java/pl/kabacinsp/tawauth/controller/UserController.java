package pl.kabacinsp.tawauth.controller;

import pl.kabacinsp.tawauth.model.User;
import pl.kabacinsp.tawauth.dto.UserDto;
import pl.kabacinsp.tawauth.dto.UserRegistrationDto;
import pl.kabacinsp.tawauth.repository.UserRepository;
import pl.kabacinsp.tawauth.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;
  private UserRepository userRepository;

  public UserController(UserService userService, UserRepository userRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @GetMapping("/current")
  public Principal getUser(Principal principal) {
    return principal;
  }

  @PostMapping
  @PreAuthorize("#oauth2.hasScope('server')")
  public UserDto createUser(@Valid @RequestBody UserRegistrationDto userRegistration) {
    User savedUser = userService.create(toUser(userRegistration));
    return toDto(savedUser);
  }

  @PutMapping
  @PreAuthorize("#oauth2.hasScope('server')")
  public UserDto updateUser(@RequestParam("id") long id, @Valid @RequestBody UserDto user)
      throws Exception {
    final User userFromDB = loadUpdatedUserFromDatabase(id);
    userFromDB.setUsername(user.getUsername());
    User updatedUser = userService.update(userFromDB);

    return toDto(updatedUser);
  }

  @DeleteMapping
  @PreAuthorize("#oauth2.hasScope('server')")
  public void deleteUser(@RequestParam("id") long id) throws Exception {
    final User userFromDB = loadUpdatedUserFromDatabase(id);
    userService.delete(userFromDB);
  }

  private UserDto toDto(User user) {
    UserDto userDto = new UserDto();
    userDto.setId(user.getId());
    userDto.setUsername(user.getUsername());
    return userDto;
  }

  private User toUser(UserRegistrationDto userRegistration) {
    User user = new User();
    user.setUsername(userRegistration.getUsername());
    user.setPassword(userRegistration.getPassword());
    user.setEmail(userRegistration.getEmail());
    return user;
  }

  private User loadUpdatedUserFromDatabase(long id) throws Exception {
    final Optional<User> userFromDB = userRepository.findById(id);

    if (userFromDB.isEmpty()) {
      throw new Exception("Cannot update user with ID " + id + ". User does not exist.");
    }
    return userFromDB.orElse(null);
  }
}
