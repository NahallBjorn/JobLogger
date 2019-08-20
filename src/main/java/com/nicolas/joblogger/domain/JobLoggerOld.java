package com.nicolas.joblogger.domain;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JobLoggerOld {

  private static boolean logToFile;
  private static boolean logToConsole;
  private static boolean logMessage;
  private static boolean logWarning;
  private static boolean logError;
  private static boolean logToDatabase;
  //Parameter never used
  private boolean initialized;
  private static Map dbParams;
  private static Logger logger;

  //This constructor is too big, we can think that the object is not well design
  //We should abstract this object as a data structure with a Builder pattern, and let the logic in another object.
  public JobLoggerOld(boolean logToFileParam, boolean logToConsoleParam, boolean logToDatabaseParam,
                      boolean logMessageParam, boolean logWarningParam, boolean logErrorParam, Map dbParamsMap) {
    logger = Logger.getLogger("MyLog");
    logError = logErrorParam;
    logMessage = logMessageParam;
    logWarning = logWarningParam;
    logToDatabase = logToDatabaseParam;
    logToFile = logToFileParam;
    logToConsole = logToConsoleParam;
    dbParams = dbParamsMap;
  }

  //Name of the method starts with uppercase.
  //Boolean parameters are bad practice since tells us that the method is doing more than one thing.
  //This method is doing a LOT of things so is breaking Single-Responsability principle
  public static void LogMessage(String messageText, boolean message, boolean warning, boolean error) throws Exception {

    //This part of code could be extracted in another method in charge of validate the message structure
    messageText.trim();
    if (messageText == null || messageText.length() == 0) {
      return;
    }

    //The method shouldn't being worrying about a configuration, since it should be just LOG in one of the three ways
    if (!logToConsole && !logToFile && !logToDatabase) {
      throw new Exception("Invalid configuration");
    }

    //The kind of message should be specified when calling the method so it just LOG
    if ((!logError && !logMessage && !logWarning) || (!message && !warning && !error)) {
      throw new Exception("Error or Warning or Message must be specified");
    }

    //This portion of code should be in another method, perhaps a static one in charge of managing DataBase connections
    Connection connection = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", dbParams.get("userName"));
    connectionProps.put("password", dbParams.get("password"));

    connection = DriverManager.getConnection("jdbc:" + dbParams.get("dbms") + "://" + dbParams.get("serverName")
            + ":" + dbParams.get("portNumber") + "/", connectionProps);

    //We should abstract the type of message in an object so we don't have to assign values aside of it.
    //This code tells us another reason to pass the type of message as parameter to this method, so it don't have
    //to worry about this logic
    int t = 0;
    if (message && logMessage) {
      t = 1;
    }

    if (error && logError) {
      t = 2;
    }

    if (warning && logWarning) {
      t = 3;
    }

    //This logic should be include in some object dedicated to log in Database
    Statement stmt = connection.createStatement();

    //This logic should be include in some object dedicated to log in a File
    String l = null;
    File logFile = new File(dbParams.get("logFileFolder") + "/logFile.txt");
    if (!logFile.exists()) {
      logFile.createNewFile();
    }
    FileHandler fh = new FileHandler(dbParams.get("logFileFolder") + "/logFile.txt");

    //This logic should be include in some object dedicated to log in Console
    ConsoleHandler ch = new ConsoleHandler();

    //We can avoid this logic if we just construct the message using the values of the type of the message
    //Should use StringBuilder too
    if (error && logError) {
      l = l + "error " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
    }

    if (warning && logWarning) {
      l = l + "warning " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
    }

    if (message && logMessage) {
      l = l + "message " + DateFormat.getDateInstance(DateFormat.LONG).format(new Date()) + messageText;
    }

    //This logic should be include in some object dedicated to log in a File
    if (logToFile) {
      logger.addHandler(fh);
      logger.log(Level.INFO, messageText);
    }

    //This logic should be include in some object dedicated to log in Console
    if (logToConsole) {
      logger.addHandler(ch);
      logger.log(Level.INFO, messageText);
    }

    //This logic should be include in some object dedicated to log in Database
    if (logToDatabase) {
      stmt.executeUpdate("insert into Log_Values('" + message + "', " + String.valueOf(t) + ")");
    }
  }


}
