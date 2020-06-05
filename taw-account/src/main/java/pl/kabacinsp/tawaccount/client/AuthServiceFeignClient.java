package pl.kabacinsp.tawaccount.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import pl.kabacinsp.tawaccount.dto.UserDto;
import pl.kabacinsp.tawaccount.dto.UserRegistrationDto;

@FeignClient(name = "taw-account")
public interface AuthServiceFeignClient {

  @PostMapping(value = "/taw/user")
  UserDto createUser(@RequestBody UserRegistrationDto user);

  @PutMapping(value = "/taw/user/{id}")
  UserDto updateUser(@RequestParam("id") long id, @RequestBody UserDto user);

  @DeleteMapping(value = "/taw/user/{id}")
  UserDto deleteUser(@RequestParam("id") long id);
}
