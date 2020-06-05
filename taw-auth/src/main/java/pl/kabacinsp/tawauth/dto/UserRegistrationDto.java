package pl.kabacinsp.tawauth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class  UserRegistrationDto implements Serializable {

  private static final long serialVersionUID = 1L;

  @NotNull @NotBlank private String username;

  @NotNull @NotBlank private String password;

  @NotNull @NotBlank private String email;
}
