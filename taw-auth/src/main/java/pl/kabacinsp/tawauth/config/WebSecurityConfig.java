package pl.kabacinsp.tawauth.config;

import org.springframework.core.annotation.Order;
import pl.kabacinsp.tawauth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.kabacinsp.tawauth.service.JwtAuthentication;

@Configuration
@Order(-10)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Autowired
  private JwtAuthentication unauthorizedHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().antMatchers("/", "/login", "/home").permitAll()
        .and()
        .authorizeRequests().antMatchers("/oauth/**").authenticated()
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and()
        .authorizeRequests().anyRequest().permitAll()
        .and()
        .formLogin().loginPage("/login")
        .usernameParameter("username").passwordParameter("password").permitAll()
        .loginProcessingUrl("/doLogin")
        .successForwardUrl("/postLogin")
        .failureUrl("/loginFailed")
        .and()
        .logout()
        .logoutSuccessUrl("/home")
        .and()
        .csrf().disable();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
