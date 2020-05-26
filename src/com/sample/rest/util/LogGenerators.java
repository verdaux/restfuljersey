package com.sample.rest.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface LogGenerators
{
	String commonLogger = "CommonCaterory";
	String exceptionLogger = "ExceptionCategory";
	
	Logger COMMON_LOGGER = LoggerFactory.getLogger(commonLogger);
	Logger EXCEPTION_LOGGER = LoggerFactory.getLogger(exceptionLogger);
	Logger LOGGER = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
}
