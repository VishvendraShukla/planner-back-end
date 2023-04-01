package com.planner.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.model.LoginCredentials;
import com.planner.security.authentication.UsernamePasswordAuthentication;
import com.planner.security.model.JWTTokenHelper;
import com.planner.utils.access.LoggedUser;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

  private final AuthenticationManager authenticationManager;

  private final JWTTokenHelper jwtTokenHelper;

  private final Map<String, String> redisRepository = new ConcurrentHashMap<>();

  public UsernamePasswordAuthFilter(
      AuthenticationManager authenticationManager, JWTTokenHelper jwtTokenHelper) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenHelper = jwtTokenHelper;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (isAuthorizationPresent(request)) {
      filterChain.doFilter(request, response);
    } else {
      try {
        BufferedRequestWrapper cachedBodyHttpServletRequest = new BufferedRequestWrapper(request);
        LoginCredentials loginCredentials = cachedBodyHttpServletRequest.getCredentials();
        String username = loginCredentials.getUsername();
        String password = loginCredentials.getPassword();
        String token = loginCredentials.getToken();
        if (!username.isEmpty() && (Objects.isNull(password) || password.isEmpty()) && (
            Objects.isNull(token) || token.isEmpty())) {
          Authentication auth = new UsernamePasswordAuthentication(username);
          try {
            Authentication authenticated = authenticationManager.authenticate(auth);
            SecurityContextHolder.getContext().setAuthentication(authenticated);
          } catch (AuthenticationException e) {
            logger.error(e);
            throw (Exception) e;
          }
          String authToken = UUID.randomUUID().toString();
          redisRepository.put(username, authToken);
          response.setHeader("token", authToken);
          LoggedUser.initUsername(username);
          filterChain.doFilter(cachedBodyHttpServletRequest, response);
        } else if (!username.isEmpty() && !password.isEmpty() && !token.isEmpty()) {
          Authentication authenticated = null;
          try {
            Authentication auth = new UsernamePasswordAuthentication(username, password);
            authenticated = authenticationManager.authenticate(auth);
          } catch (AuthenticationException e) {
            logger.error(e);
            throw (Exception) e;
          }
          if (redisRepository.containsKey(username)) {
            String authToken = redisRepository.get(username);
            redisRepository.remove(username);
            if (!authToken.equalsIgnoreCase(token)) {
              throw new BadCredentialsException("Bad Credentials");
            }
            String jwtToken = jwtTokenHelper.generateJwtToken(
                Objects.requireNonNull(authenticated));
            SecurityContextHolder.getContext().setAuthentication(authenticated);
            response.setHeader("Authorization", jwtToken);
            LoggedUser.initUsername(username);
            filterChain.doFilter(cachedBodyHttpServletRequest, response);
          } else {
            throw new BadCredentialsException("Bad Credentials");
          }
        }
      } catch (Exception e) {
        logger.error(e.getMessage(), e);
        throw new BadCredentialsException("Bad Credentials");
      }
    }
  }


  private boolean isAuthorizationPresent(HttpServletRequest request) {
    try {
      String auth = request.getHeader("Authorization");
      if (auth != null) {
        return true;
      }
      return false;
    } catch (Exception e) {

    }
    return false;
  }


  public static final class BufferedRequestWrapper extends HttpServletRequestWrapper {

    private ByteArrayInputStream bais = null;
    private ByteArrayOutputStream baos;
    private BufferedServletInputStream bsis = null;
    private byte[] buffer;

    public BufferedRequestWrapper(HttpServletRequest req) throws IOException {
      super(req);
      // Read InputStream and store its content in a buffer.
      InputStream is = req.getInputStream();
      this.baos = new ByteArrayOutputStream();
      byte[] buf = new byte[1024];
      int read;
      while ((read = is.read(buf)) > 0) {
        this.baos.write(buf, 0, read);
      }
      this.buffer = this.baos.toByteArray();
    }

    @Override
    public ServletInputStream getInputStream() {
      this.bais = new ByteArrayInputStream(this.buffer);
      this.bsis = new BufferedServletInputStream(this.bais);
      return this.bsis;
    }

    @SneakyThrows
    public LoginCredentials getCredentials() {
      String body = null;
      StringBuilder stringBuilder = new StringBuilder();
      BufferedReader bufferedReader = new BufferedReader(
          new InputStreamReader(this.getInputStream()));
      try {
        if (bufferedReader != null) {
          char[] charBuffer = new char[128];
          int bytesRead = -1;
          while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
            stringBuilder.append(charBuffer, 0, bytesRead);
          }
        } else {
          stringBuilder.append("");
        }
      } catch (IOException ex) {
        throw ex;
      } finally {
        if (bufferedReader != null) {
          try {
            bufferedReader.close();
          } catch (IOException ex) {
            throw ex;
          }
        }
      }
      body = stringBuilder.toString();
      body.replaceAll("\n", "");
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(body, LoginCredentials.class);
    }
  }

  private static final class BufferedServletInputStream extends ServletInputStream {

    private ByteArrayInputStream bais;

    public BufferedServletInputStream(ByteArrayInputStream bais) {
      this.bais = bais;
    }

    @Override
    public int available() {
      return this.bais.available();
    }

    @Override
    public int read() {
      return this.bais.read();
    }

    @Override
    public int read(byte[] buf, int off, int len) {
      return this.bais.read(buf, off, len);
    }

    @Override
    public boolean isFinished() {
      return false;
    }

    @Override
    public boolean isReady() {
      return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {
      //Do nothing
    }
  }

}


