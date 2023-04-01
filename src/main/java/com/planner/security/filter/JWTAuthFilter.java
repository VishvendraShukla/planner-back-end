package com.planner.security.filter;

import com.planner.security.authentication.JWTTokenAuthentication;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;

  private Map<String, String> redisRepository = new ConcurrentHashMap<>();

  public JWTAuthFilter(
      AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (request.getHeader("Authorization") != null) {
      String bearerToken = parseJWT(request);
      Authentication authentication = new JWTTokenAuthentication(bearerToken);
      Authentication authenticated = authenticationManager.authenticate(authentication);
      SecurityContextHolder.getContext().setAuthentication(authenticated);
      request.removeAttribute("Authorization");
    }
    filterChain.doFilter(request, response);
  }

  private String parseJWT(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    String token = "";
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      token = bearerToken.substring(7, bearerToken.length());
    } else {
      throw new BadCredentialsException("Bad Credentials");
    }
    return token;
  }
}
