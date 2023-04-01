package com.planner.config;

import com.planner.exception.handler.security.SecurityExceptionHandler;
import com.planner.security.filter.JWTAuthFilter;
import com.planner.security.filter.UsernamePasswordAuthFilter;
import com.planner.security.model.JWTTokenHelper;
import com.planner.security.provider.JWTAuthTokenProvider;
import com.planner.security.provider.UsernameAuthProvider;
import java.util.Arrays;
import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class PlannerSecurityConfig extends WebSecurityConfigurerAdapter {

  @Resource(name = "usernameAuthProvider")
  private UsernameAuthProvider usernameAuthProvider;

  @Resource(name = "jwtAuthTokenProvider")
  private JWTAuthTokenProvider jwtAuthTokenProvider;

  @Resource(name = "jwtTokenHelper")
  private JWTTokenHelper jwtTokenHelper;

  @Resource(name = "securityExceptionHandler")
  private SecurityExceptionHandler securityExceptionHandler;

  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return new ProviderManager(Arrays.asList(usernameAuthProvider, jwtAuthTokenProvider));
  }

  String[] accessPaths = {"/access/login", "/access/generate", "/access/generate/token"};

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.authorizeRequests()
        .antMatchers(accessPaths).hasRole("USER")
        .and()
        .httpBasic()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(securityExceptionHandler)
        .and()
        .cors()
        .and()
        .csrf()
        .disable();

    http.authorizeRequests()
        .antMatchers("/user/**", "/admin/**", "/task/**", "task-category/**").hasRole("USER")
        .anyRequest().authenticated()
        .and()
        .httpBasic()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(securityExceptionHandler)
        .and()
        .cors()
        .and()
        .csrf()
        .disable();

    http.addFilterBefore(
            new UsernamePasswordAuthFilter(authenticationManagerBean(), jwtTokenHelper),
            BasicAuthenticationFilter.class)
        .addFilterBefore(new JWTAuthFilter(authenticationManagerBean()),
            BasicAuthenticationFilter.class);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/favicon.ico", "/WEB-INF/view/error.jsp","/access/sign-up");
  }
}
