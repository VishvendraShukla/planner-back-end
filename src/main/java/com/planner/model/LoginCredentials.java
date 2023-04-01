package com.planner.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginCredentials {

  @JsonProperty("username")
  private String username;
  @JsonProperty("password")
  private String password;
  @JsonProperty("token")
  private String token;

}
