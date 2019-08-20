package com.nicolas.joblogger.crosscutting.logger.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageTypeEnum {
  MESSAGE("MESSAGE", 1),  ERROR("ERROR", 2), WARNING("WARNING", 3);

  private String description;
  private int code;

}
