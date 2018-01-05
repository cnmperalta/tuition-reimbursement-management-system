package com.revature.projects.trms.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.revature.projects.trms.util.ConnectionUtility;

public abstract class GenericDataAccessObject<T> implements DataAccessObject<T>{
  protected Connection connection;

  public GenericDataAccessObject() {
    try {
      connection =  ConnectionUtility.getConnection();
    } catch (SQLException e) {
      System.err.println("Error getting DB connection.");
    } catch (IOException e) {
      System.err.println("Error reading connection.properties file.");
    }
  }

  public void checkConnection() {
    try {
      if(connection.isClosed()) {
        connection = ConnectionUtility.getConnection();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      connection.close();
    } catch (SQLException e) {
      System.err.println("Error closing connection.");
    }
  }

  @Override
  protected void finalize() throws Throwable {
    super.finalize();
    this.close();
  }
}