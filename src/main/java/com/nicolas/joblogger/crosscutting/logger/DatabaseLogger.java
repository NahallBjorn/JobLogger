package com.nicolas.joblogger.crosscutting.logger;

import com.nicolas.joblogger.crosscutting.DatabaseConnection;
import com.nicolas.joblogger.crosscutting.exception.LoggerException;
import com.nicolas.joblogger.crosscutting.logger.enumeration.MessageTypeEnum;
import lombok.AllArgsConstructor;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@AllArgsConstructor
public class DatabaseLogger implements JobLogger {

  @Override public void logMessage(MessageTypeEnum messageType, String message) throws LoggerException, SQLException {
    validateMessage(message);
    Connection connection = DatabaseConnection.getInstance().getConnection();
    message = new StringBuilder().append(messageType.getDescription())
            .append(" ")
            .append(getActualDate())
            .append(" ")
            .append(message)
            .toString();
    Statement stmt = connection.createStatement();
    stmt.executeUpdate("insert into Log_Values('" + message + "', " + String.valueOf(messageType.getCode()) + ")");
  }
}
