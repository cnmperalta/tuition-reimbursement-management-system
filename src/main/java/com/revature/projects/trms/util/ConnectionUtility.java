package com.revature.projects.trms.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import oracle.jdbc.driver.OracleDriver;

public class ConnectionUtility {
  private static Connection connection = null;

  public static Connection getConnection() throws SQLException, IOException {
    if(connection == null || connection.isClosed()) {
      // Properties properties = new Properties();
      // InputStream in = new FileInputStream("classes/connection.properties");
      String url, user, password;

      // properties.load(in);
      // url = properties.getProperty("url");
      // user = properties.getProperty("user");
      // password = properties.getProperty("password");
      url = "jdbc:oracle:thin:@localhost:1521:xe";
      user = "trmsuser";
      password = "p4ssw0rd";

      DriverManager.registerDriver(new OracleDriver());

      connection = DriverManager.getConnection(url, user, password);
    }

    return connection;
  }
}