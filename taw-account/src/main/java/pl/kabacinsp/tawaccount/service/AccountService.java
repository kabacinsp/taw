package pl.kabacinsp.tawaccount.service;

import org.springframework.web.bind.annotation.*;
import pl.kabacinsp.tawaccount.dto.UserDto;
import pl.kabacinsp.tawaccount.dto.UserRegistrationDto;

public interface AccountService {

  @PostMapping(value = "/accounts")
  UserDto create(@RequestBody UserRegistrationDto user);

  @PutMapping(value = "/accounts/update")
  UserDto update(@RequestParam("id") long id, @RequestBody UserDto user);

  @DeleteMapping(value = "/accounts/delete")
  UserDto delete(@RequestParam("id") long id);
}
