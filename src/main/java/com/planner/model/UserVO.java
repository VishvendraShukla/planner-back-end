package com.planner.model;

import com.planner.utils.IdVO;
import java.util.Calendar;

public class UserVO extends IdVO {

  private String username;
  private Calendar lastModified;
  private String userType;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Calendar getLastModified() {
    return lastModified;
  }

  public void setLastModified(Calendar lastModified) {
    this.lastModified = lastModified;
  }

  public String getUserType() {
    return userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }
}
