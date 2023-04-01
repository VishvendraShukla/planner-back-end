package com.planner.utils.access;

import com.planner.entities.Users;
import java.util.HashMap;
import java.util.Map;

public class LoggedUser {

  private static String username;

  private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

  public static void init(Users user) {
    createUserThread(user);
  }

  public static void createUserThread(Users user) {
    Map<String, Object> map = new HashMap<>();
    map.put("User", user);
    threadLocal.set(map);
  }

  public static Users getUser() {
    return (Users) threadLocal.get().get("User");
  }

  public static void initUsername(String username) {
    LoggedUser.username = username;
  }

  public static String getUsername() {
    return username;
  }

  public static void getSize() {
    System.out.println(threadLocal.get().size());
  }

}
