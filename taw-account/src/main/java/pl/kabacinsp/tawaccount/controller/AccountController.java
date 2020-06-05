package pl.kabacinsp.tawaccount.controller;

import org.springframework.web.bind.annotation.*;
import pl.kabacinsp.tawaccount.dto.UserDto;
import pl.kabacinsp.tawaccount.dto.UserRegistrationDto;
import pl.kabacinsp.tawaccount.service.AccountService;

@RestController
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @PostMapping
  public UserDto createNewAccount(@RequestBody UserRegistrationDto user) {
    return accountService.create(user);
  }

  @PutMapping
  public UserDto updateAccount(@PathVariable("id") long id, @RequestBody UserDto user) {
    return accountService.update(id, user);
  }

  @DeleteMapping
  public UserDto deleteAccount(@PathVariable("id") long id) {
    return accountService.delete(id);
  }
}
