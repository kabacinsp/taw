package pl.kabacinsp.tawauth.service;

import pl.kabacinsp.tawauth.model.UserClient;

public interface UserService {
  UserClient create(UserClient userClient);

  UserClient update(UserClient userClient) throws Exception;

  UserClient delete(UserClient userClient);
}
