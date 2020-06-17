package pl.kabacinsp.tawauth.dto;

import org.springframework.security.core.GrantedAuthority;
import pl.kabacinsp.tawauth.enums.Authorities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDto implements Serializable {

  private static final long serialVersionUID = 1L;

  private long id;

  private String username;

  private String email;

  private String authorities;

  private boolean activated;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean isActivated() {
    return activated;
  }

  public void setActivated(boolean activated) {
    this.activated = activated;
  }

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
