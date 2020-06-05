package pl.kabacinsp.tawaccount.service;

import pl.kabacinsp.tawaccount.client.AuthServiceFeignClient;
import pl.kabacinsp.tawaccount.dto.UserDto;
import pl.kabacinsp.tawaccount.dto.UserRegistrationDto;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

  private final AuthServiceFeignClient authServiceFeignClient;

  public AccountServiceImpl(AuthServiceFeignClient authServiceFeignClient) {
    this.authServiceFeignClient = authServiceFeignClient;
  }

  @Override
  public UserDto create(UserRegistrationDto user) {
    return authServiceFeignClient.createUser(user);
  }

  @Override
  public UserDto update(long id, UserDto user) {
    return authServiceFeignClient.updateUser(id, user);
  }

  @Override
  public UserDto delete(long id) {
    return authServiceFeignClient.deleteUser(id);
  }
}
