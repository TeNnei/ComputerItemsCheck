package com.items.check.startVersion1.DataBaseFolder;

import com.items.check.startVersion1.integration.ItemsForDataBaseCheck;
import io.vertx.core.Promise;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnectivity {
  static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/postgres";
  static final String USER = "postgres";
  static final String PASS = "987346521";

  public static Connection getDbConnection(){
    Connection connection = null;
    try {
      connection = DriverManager.getConnection(DB_URL, USER, PASS);
    } catch (SQLException throwables) {
      throwables.printStackTrace();
    }
    return connection;
  }

  public static Promise<List<ItemsForDataBaseCheck>> checkIfNotExist (ItemsForDataBaseCheck items) throws SQLException {
    Promise<List<ItemsForDataBaseCheck>> promise = Promise.promise();
    List<ItemsForDataBaseCheck> list = new ArrayList<>();
    Connection connection = getDbConnection();
    String sql = "SELECT * FROM items_status WHERE computer_title = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, items.getComputerTitle());
    ResultSet rs = preparedStatement.executeQuery();
    while(rs.next()){
      ItemsForDataBaseCheck item = new ItemsForDataBaseCheck()
        .setId(rs.getString("id"))
        .setComputerTitle(rs.getString("computer_title"))
        .setGPU(rs.getString("gpu"))
        .setRAM(rs.getString("ram"))
        .setProcessor(rs.getString("processor"))
        .setSSD(rs.getString("ssd"))
        .setDateOfLastStatus(LocalDateTime.ofInstant(Instant.ofEpochMilli(rs.getLong("date_of_status")), ZoneOffset.UTC));
      list.add(item);
    }
    promise.complete(list);
    return promise;
  }

  public static Promise<Boolean> insertForCheck (ItemsForDataBaseCheck items) throws SQLException {
    Promise<Boolean> promise = Promise.promise();
    Connection connection = getDbConnection();
    String sql = "INSERT INTO items_status (computer_title, gpu, ram, processor, ssd, date_of_status) VALUES (?,?,?,?,?,?)";
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, items.getComputerTitle());
    preparedStatement.setString(2, items.getGPU());
    preparedStatement.setString(3, items.getRAM());
    preparedStatement.setString(4, items.getProcessor());
    preparedStatement.setString(5, items.getSSD());
    preparedStatement.setLong(6, LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli());
    preparedStatement.executeUpdate();
    promise.complete(true);
    return promise;
  }
}
