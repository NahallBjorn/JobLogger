package com.nicolas.joblogger.crosscutting.logger;

import com.nicolas.joblogger.crosscutting.exception.LoggerException;
import com.nicolas.joblogger.crosscutting.logger.enumeration.MessageTypeEnum;
import lombok.AllArgsConstructor;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@AllArgsConstructor
public class ConsoleLogger implements JobLogger {

  private static final Logger logger = Logger.getLogger(ConsoleLogger.class.getName());

  @Override public void logMessage(MessageTypeEnum messageType, String message) throws LoggerException {
    validateMessage(message);
    ConsoleHandler consoleHandler = new ConsoleHandler();
    logger.addHandler(consoleHandler);
    logger.log(Level.INFO, new StringBuilder().append(messageType.getDescription())
            .append(" ")
            .append(getActualDate())
            .append(" ")
            .append(message)
            .toString());
  }
}
