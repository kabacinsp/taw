package pl.kabacinsp.tawauth.config;

import org.springframework.security.oauth2.provider.token.store.*;
import pl.kabacinsp.tawauth.service.AuthClientDetailsService;
import pl.kabacinsp.tawauth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.net.MalformedURLException;
import java.net.URL;

@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

  @Autowired
  @Qualifier("authenticationManagerBean")
  private AuthenticationManager authenticationManager;

  @Autowired private CustomUserDetailsService userDetailsService;

  @Autowired private AuthClientDetailsService authClientDetailsService;

  @Autowired private PasswordEncoder encoder;

  private String privateKey =
      "yourPrivateKey"; // This should come from a environment variable in production

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(authClientDetailsService);
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(tokenEnhancer());
  }

  @Bean
  public JwtAccessTokenConverter tokenEnhancer() {
    JwtAccessTokenConverter accessToken = new JwtAccessTokenConverter();
    accessToken.setSigningKey(privateKey);
    accessToken.setJwtClaimsSetVerifier(issuerClaimVerifier());
    return accessToken;
  }

  @Bean
  public JwtClaimsSetVerifier issuerClaimVerifier() {
    try {
      return new IssuerClaimVerifier(new URL("http://localhost:8081"));
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .authenticationManager(this.authenticationManager)
        .tokenStore(tokenStore())
        .accessTokenConverter(tokenEnhancer())
        .userDetailsService(this.userDetailsService);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
    oauthServer
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
        .passwordEncoder(encoder)
        .allowFormAuthenticationForClients();
  }
}
