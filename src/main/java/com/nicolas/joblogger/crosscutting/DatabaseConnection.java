package com.nicolas.joblogger.crosscutting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

  private static DatabaseConnection instance;
  private Connection connection;
  private String driver = "com.mysql.jdbc.Driver";
  private String url = "jdbc:mysql://localhost:5432/testdb";
  private String username = "root";
  private String password = "localhost";


  private DatabaseConnection() {
    try {
      Class.forName(driver);
      this.connection = DriverManager.getConnection(url, username, password);
    } catch (ClassNotFoundException ex) {
      System.out.println("Database Connection Creation Failed : " + ex.getMessage());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public Connection getConnection() {
    return connection;
  }

  public static DatabaseConnection getInstance() throws SQLException {
    if (instance == null) {
      instance = new DatabaseConnection();
    } else if (instance.getConnection().isClosed()) {
      instance = new DatabaseConnection();
    }

    return instance;
  }

}
