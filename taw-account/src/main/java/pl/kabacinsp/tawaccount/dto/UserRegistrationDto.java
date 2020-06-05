package pl.kabacinsp.tawaccount.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private String username;

  private String password;
}
