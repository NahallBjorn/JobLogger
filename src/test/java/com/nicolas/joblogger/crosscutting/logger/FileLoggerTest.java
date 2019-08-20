package com.nicolas.joblogger.crosscutting.logger;

import com.nicolas.joblogger.crosscutting.logger.enumeration.MessageTypeEnum;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileLoggerTest {

  private File file;
  private JobLogger jobLogger;

  @Before
  public void setUp() throws IOException {
    file = File.createTempFile("logFile", ".txt");
    jobLogger = new FileLogger(file.getAbsolutePath());
  }

  @After
  public void setDown() {
    file.deleteOnExit();
  }

  @Test
  public void logOnFile() throws Exception {
    String expected = "Testing file logger";
    jobLogger.logMessage(MessageTypeEnum.MESSAGE, expected);
    Stream<String> lines = Files.lines(Paths.get(file.getAbsolutePath()));
    String actual = lines.collect(Collectors.joining(System.lineSeparator()));
    Assert.assertTrue(actual.contains(expected));
  }

}