package pl.kabacinsp.tawauth.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "oauth_client")
@Getter
@Setter
public class AuthClient implements ClientDetails {

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", columnDefinition = "serial")
  private long id;

  @Column(name = "client_id")
  private String clientId;

  @Column(name = "client_secret")
  private String clientSecret;

  @Column(name = "grant_types")
  private String grantTypes;

  @Column(name = "scopes")
  private String scopes;

  @Column(name = "resources")
  private String resources;

  @Column(name = "redirect_uris")
  private String redirectUris;

  @Column(name = "access_token_validity")
  private Integer accessTokenValidity;

  @Column(name = "refresh_token_validity")
  private Integer refreshTokenValidity;

  @Column(name = "additional_information")
  private String additionalInformation;

  @Override
  public Set<String> getResourceIds() {
    return resources != null ? new HashSet<>(Arrays.asList(resources.split(","))) : null;
  }

  @Override
  public boolean isSecretRequired() {
    return true;
  }

  @Override
  public boolean isScoped() {
    return false;
  }

  @Override
  public Set<String> getScope() {
    return scopes != null ? new HashSet<>(Arrays.asList(scopes.split(","))) : null;
  }

  @Override
  public Set<String> getAuthorizedGrantTypes() {
    return grantTypes != null ? new HashSet<>(Arrays.asList(grantTypes.split(","))) : null;
  }

  @Override
  public Set<String> getRegisteredRedirectUri() {
    return redirectUris != null ? new HashSet<>(Arrays.asList(redirectUris.split(","))) : null;
  }

  @Override
  public Collection<GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  @Override
  public Integer getAccessTokenValiditySeconds() {
    return accessTokenValidity;
  }

  @Override
  public Integer getRefreshTokenValiditySeconds() {
    return refreshTokenValidity;
  }

  @Override
  public boolean isAutoApprove(String scope) {
    return true;
  }

  @Override
  public Map<String, Object> getAdditionalInformation() {
    return null;
  }
}
