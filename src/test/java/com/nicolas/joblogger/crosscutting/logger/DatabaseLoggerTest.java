package com.nicolas.joblogger.crosscutting.logger;

import com.nicolas.joblogger.crosscutting.logger.enumeration.MessageTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseLoggerTest {

  @Mock
  private DatabaseLogger databaseLogger;

  @Test
  public void logOnConsole() throws Exception {
    String expected = "Testing console logger";
    databaseLogger.logMessage(MessageTypeEnum.MESSAGE, expected);
    verify(databaseLogger, atLeastOnce()).logMessage(Mockito.any(), Mockito.anyString());
  }

}