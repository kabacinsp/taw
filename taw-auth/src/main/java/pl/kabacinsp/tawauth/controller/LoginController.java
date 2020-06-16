package pl.kabacinsp.tawauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import pl.kabacinsp.tawauth.model.UserClient;
import pl.kabacinsp.tawauth.service.UserService;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

  @Autowired
  private UserService userService;

  @GetMapping("/login")
  public ModelAndView require() {
    return new ModelAndView("login");
  }

  @GetMapping("/welcome")
  public ModelAndView welcome() {
    return new ModelAndView("welcome");
  }

  @GetMapping(value = "/loginFailed")
  public ModelAndView loginError(Model model) {
    model.addAttribute("error", "true");
    return new ModelAndView("login");
  }

  @RequestMapping("/error-forbidden")
  public String errorForbidden() {
    return "error-forbidden";
  }

  @GetMapping(value = "/logout")
  public ModelAndView logout(SessionStatus session) {
    SecurityContextHolder.getContext().setAuthentication(null);
    session.setComplete();
    return new ModelAndView("home");
  }

  @PostMapping(value = "/postLogin")
  public ModelAndView postLogin(Model model, HttpSession session) {

    // read principal out of security context and set it to session
    UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    validatePrinciple(authentication.getPrincipal());
    UserClient loggedInUserClient = new UserClient();

    model.addAttribute("currentUserId", loggedInUserClient.getId());
    model.addAttribute("currentUser", loggedInUserClient.getUsername());
    session.setAttribute("userId", loggedInUserClient.getId());
    return new ModelAndView("welcome");
  }

  @PostMapping(value = "/register")
  public ModelAndView createUser(UserClient userClient) {
    userService.create(userClient);
    return new ModelAndView("welcome");
  }

  private void validatePrinciple(Object principal) {
    if (!(principal instanceof UserClient)) {
      throw new  IllegalArgumentException("Principal can not be null!");
    }
  }
}