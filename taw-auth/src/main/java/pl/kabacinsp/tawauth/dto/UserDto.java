package pl.kabacinsp.tawauth.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;

  private String username;
}
