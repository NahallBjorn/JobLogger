package com.nicolas.joblogger.crosscutting.logger;

import com.nicolas.joblogger.crosscutting.exception.LoggerException;
import com.nicolas.joblogger.crosscutting.logger.enumeration.MessageTypeEnum;

import java.text.DateFormat;
import java.util.Date;

public interface JobLogger {

  void logMessage(MessageTypeEnum messageType, String message) throws Exception;

  default void validateMessage(String message) throws LoggerException {
    message.trim();
    if (message == null || message.length() == 0) {
      throw new LoggerException("No message to log");
    }
  }

  default String getActualDate() {
    return DateFormat.getDateInstance(DateFormat.LONG).format(new Date());
  }

}
