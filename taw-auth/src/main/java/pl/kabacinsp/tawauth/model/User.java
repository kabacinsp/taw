package pl.kabacinsp.tawauth.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.kabacinsp.tawauth.enums.Authorities;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@EqualsAndHashCode
public class User implements UserDetails {

  @Id
  @Column(name = "id", columnDefinition = "serial")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Email
  @Column(name = "email")
  private String email;

  @Column(name = "activated")
  private boolean isActivated;

  @Column(name = "activation_key")
  private String activationKey;

  @Column(name = "last_login")
  private LocalDateTime lastLogin;

  @Column(name = "authorities")
  private String authorities;

  @Override
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

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return isActivated;
  }

  public boolean isActivated() {
    return isActivated;
  }
}
