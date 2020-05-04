package com.sample.rest.util;

import java.lang.invoke.MethodHandles;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class Constants
{
	public static Properties config;
	public static Properties db;
	
	public static String title;
	public static String procDB;
	public static String procMsg;
	
	public static final String commonLogger="CommonCategory";
	public static final String exceptionLogger="ExceptionCategory";
	
	public static final Logger COMMON_LOGGER  = LoggerFactory.getLogger(commonLogger);
	public static final Logger EXCEPTION_LOGGER  = LoggerFactory.getLogger(exceptionLogger);
	public static final Logger LOGGER  = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	
	
	static
	{
		try
		{
			config = PropertyLoader.loadPropValues("config.properties");
			db = PropertyLoader.loadPropValues("db.properties");
			
			title = config.getProperty("title");
			procDB = db.getProperty("findcountbyid");
			procMsg = db.getProperty("messageById");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	

}
