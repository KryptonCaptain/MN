package com.trinarybrain.magianaturalis.common.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log
{
  public static final Logger logger = LogManager.getLogger("magianaturalis");
  
  public static void initLog()
  {
    logger.info(String.format("Starting Magia Naturalis ", new Object[] { "0.1.5b" }));
    logger.info("Copyright (c) TrinaryBrain 2015");
  }
  
  public static void logInRed(String str)
  {
    logger.error(str);
  }
}
