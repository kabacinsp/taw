package pl.kabacinsp.tawauth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistration {

  private long id;
  private String username;
  private String email;
  private String password;
  private String authorities;

  public UserRegistration(){}

  public UserRegistration(long id, String email, String password, String authorities) {
    this.id = id;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }
}
