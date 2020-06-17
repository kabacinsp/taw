package pl.kabacinsp.tawauth.dto;

import org.springframework.security.core.GrantedAuthority;
import pl.kabacinsp.tawauth.enums.Authorities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;

  private String username;

  private String email;

  private String authorities;

  private boolean activated;

  public List<GrantedAuthority> getAuthorities() {
    return authorities != null
        ? Arrays.stream(authorities.split(","))
        .map(Authorities::valueOf)
        .collect(Collectors.toList())
        : null;
  }

  public void setAuthorities(Set<Authorities> authorities) {
    this.authorities =
        authorities.stream().map(Authorities::getAuthority).collect(Collectors.joining(","));
  }
}
