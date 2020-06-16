package pl.kabacinsp.tawauth.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import pl.kabacinsp.tawauth.model.UserClient;
import pl.kabacinsp.tawauth.dto.UserDto;
import pl.kabacinsp.tawauth.dto.UserRegistrationDto;
import pl.kabacinsp.tawauth.model.UserRegistration;
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

  @PutMapping
  @PreAuthorize("#oauth2.hasScope('server')")
  public UserDto updateUser(@RequestParam("id") long id, @Valid @RequestBody UserDto user)
      throws Exception {
    final UserClient userClientFromDB = loadUpdatedUserFromDatabase(id);
    userClientFromDB.setUsername(user.getUsername());
    UserClient updatedUserClient = userService.update(userClientFromDB);

    return toDto(updatedUserClient);
  }

  @DeleteMapping
  @PreAuthorize("#oauth2.hasScope('server')")
  public void deleteUser(@RequestParam("id") long id) throws Exception {
    final UserClient userClientFromDB = loadUpdatedUserFromDatabase(id);
    userService.delete(userClientFromDB);
  }

  private UserDto toDto(UserClient userClient) {
    UserDto userDto = new UserDto();
    userDto.setId(userClient.getId());
    userDto.setUsername(userClient.getUsername());
    return userDto;
  }

  private UserClient toUser(UserRegistration userRegistration) {
    UserClient userClient = new UserClient();
    userClient.setUsername(userRegistration.getUsername());
    userClient.setPassword(userRegistration.getPassword());
    userClient.setEmail(userRegistration.getEmail());
    return userClient;
  }

  private UserClient loadUpdatedUserFromDatabase(long id) throws Exception {
    final Optional<UserClient> userFromDB = userRepository.findById(id);

    if (userFromDB.isEmpty()) {
      throw new Exception("Cannot update user with ID " + id + ". User does not exist.");
    }
    return userFromDB.orElse(null);
  }
}
