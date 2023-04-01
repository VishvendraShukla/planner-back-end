package com.planner.utils.startup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.planner.model.Roles;
import com.planner.model.UserCredentials;
import com.planner.service.AdminService;
import com.planner.service.RoleService;
import com.planner.service.UserService;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

  private final Logger logger = LoggerFactory.getLogger(StartupRunner.class);

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.driver-class-name}")
  private String className;

  private final UserService userService;

  private final AdminService adminService;

  private final RoleService roleService;

  public StartupRunner(UserService userService, AdminService adminService, RoleService roleService,
      com.planner.service.RoleService roleService1) {
    this.userService = userService;
    this.adminService = adminService;
    this.roleService = roleService;
  }


  @Override
  public void run(String... args) throws Exception {
    boolean isUserTableEmpty = false;
    boolean tablesExists = false;
    boolean dbExists = false;
    String dbName = "todo_planner";
    Connection connection = null;
    ResultSet resultSet = null;
    ResultSet tableResultSet = null;
    try {
      Class.forName(className);
      connection = DriverManager.getConnection(dbUrl, username, password);
      resultSet = connection.getMetaData().getCatalogs();

      while (resultSet.next()) {
        String catalogs = resultSet.getString(1);
        if (dbName.equals(catalogs)) {
          dbExists = true;
          break;
        }
      }
      tablesExists = tablesExistsSQL(connection, dbName);
      isUserTableEmpty = isUserTableEmpty(connection);
    } catch (Exception e) {
      logger.error(e.getMessage());
    } finally {
      connection.close();
    }

    if (tablesExists && isUserTableEmpty) {
      logger.info("Populating Database.......");
      populateDatabase();
      logger.info("Populated Database Successfully.......");
    } else if (!dbExists) {
      logger.info("Database does not exists.......");
    } else {
      logger.info("Database already exists.......");
    }
  }

  void populateDatabase() {
    ObjectMapper mapper = new ObjectMapper();
    logger.info("Creating Roles.......");
    TypeReference<List<Roles>> roleTypeReference = new TypeReference<List<Roles>>() {
    };
    logger.info("Creating Users.......");
    TypeReference<List<UserCredentials>> userTypeReference = new TypeReference<List<UserCredentials>>() {
    };
    TypeReference<List<UserCredentials>> adminTypeReference = new TypeReference<List<UserCredentials>>() {
    };
    InputStream userInputStream = TypeReference.class.getResourceAsStream(
        "/static/user/static-users.json");
    InputStream adminInputStream = TypeReference.class.getResourceAsStream(
        "/static/admin/static-admin.json");
    InputStream roleInputStream = TypeReference.class.getResourceAsStream(
        "/static/roles/static-role.json");
    try {
      logger.info("Roles Created.......");
      List<Roles> roles = mapper.readValue(roleInputStream,roleTypeReference);
      roleService.saveAll(roles);
      logger.info("Users Created.......");
      List<UserCredentials> users = mapper.readValue(userInputStream, userTypeReference);
      userService.saveAll(users);
      logger.info("Users saved..........");
      logger.info("Creating Admins.......");
      List<UserCredentials> admins = mapper.readValue(adminInputStream, adminTypeReference);
      adminService.saveAllAdmin(admins);
      logger.info("Admins saved..........");
    } catch (IOException e) {
      logger.info("Unable to save users: " + e.getMessage());
    }
  }

  private boolean tablesExistsSQL(Connection connection, String dbName) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
        + "FROM information_schema.tables "
        + "WHERE TABLE_SCHEMA = ?");
    preparedStatement.setString(1, dbName);

    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return resultSet.getInt(1) != 0;
  }

  private boolean isUserTableEmpty(Connection connection) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
        + "FROM todo_planner.users ");
    ResultSet resultSet = preparedStatement.executeQuery();
    resultSet.next();
    return resultSet.getInt(1) == 0;
  }

}
