package pl.kabacinsp.tawauth.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.kabacinsp.tawauth.dto.UserDto;
import pl.kabacinsp.tawauth.model.UserClient;
import pl.kabacinsp.tawauth.repository.UserRepository;
import pl.kabacinsp.tawauth.service.UserService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AdminController {

  private final UserService userService;
  private final UserRepository userRepository;

  public AdminController(UserService userService, UserRepository userRepository) {
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @GetMapping("/manage")
  public ModelAndView manage() {
    ModelAndView mav = new ModelAndView("manage");
    mav.addObject("users", userRepository.findAll());
    return mav;
  }

  @GetMapping(value = "/delete_user")
  public ModelAndView handleDeleteUser(@RequestParam(name="userId") long userId) throws Exception {
    final UserClient userClientFromDB = loadUpdatedUserFromDatabase(userId);
    userService.delete(userClientFromDB);
    return new ModelAndView("redirect:/manage");
  }

  @GetMapping(value = "/edit_user")
  public ModelAndView handleDeleteUser(@RequestParam(name="userId") long userId, Model model) throws Exception {
    model.addAttribute("editingUser", loadUpdatedUserFromDatabase(userId));
    return new ModelAndView("edit");
  }

  @PutMapping(value = "/edit_user")
  public ModelAndView updateUser(@RequestParam("id") long id, @Valid @RequestBody UserDto user)
      throws Exception {
    final UserClient userClientFromDB = loadUpdatedUserFromDatabase(id);
    userClientFromDB.setUsername(user.getUsername());
    userClientFromDB.setEmail(user.getEmail());
    userClientFromDB.setActivated(user.isActivated());
    UserClient updatedUserClient = userService.update(userClientFromDB);

    toDto(updatedUserClient);
    return new ModelAndView("manage");
  }

  private UserClient loadUpdatedUserFromDatabase(long id) throws Exception {
    final Optional<UserClient> userFromDB = userRepository.findById(id);

    if (userFromDB.isEmpty()) {
      throw new Exception("Cannot update user with ID " + id + ". User does not exist.");
    }
    return userFromDB.orElse(null);
  }

  private UserDto toDto(UserClient userClient) {
    UserDto userDto = new UserDto();
    userDto.setId(userClient.getId());
    userDto.setUsername(userClient.getUsername());
    userDto.setActivated(userClient.isActivated());
    return userDto;
  }
}
