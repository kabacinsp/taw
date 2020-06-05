package pl.kabacinsp.tawauth.service;

import pl.kabacinsp.tawauth.model.User;

public interface UserService {
  User create(User user);

  User update(User user) throws Exception;

  User delete(User user);
}
