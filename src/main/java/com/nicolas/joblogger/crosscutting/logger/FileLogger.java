package com.nicolas.joblogger.crosscutting.logger;


import com.nicolas.joblogger.crosscutting.exception.LoggerException;
import com.nicolas.joblogger.crosscutting.logger.enumeration.MessageTypeEnum;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
public class FileLogger implements JobLogger {

  private String filePath;
  private static final Logger logger = Logger.getLogger(FileLogger.class.getName());

  @Override public void logMessage(MessageTypeEnum messageType, String message) throws LoggerException, IOException {
    validateMessage(message);
    validateFile(filePath);
    FileHandler fileHandler = new FileHandler(filePath);
    logger.addHandler(fileHandler);
    logger.log(Level.INFO, new StringBuilder().append(messageType.getDescription())
            .append(" ")
            .append(getActualDate())
            .append(" ")
            .append(message)
            .toString());
  }

  private void validateFile(String file) throws IOException {
    File logFile = new File(file);
    if (!logFile.exists()) {
      logFile.createNewFile();
    }
  }

}
