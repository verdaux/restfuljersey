package com.sample.rest.util;

import java.util.Properties;

public class Constants
{
	public static Properties config;
	public static Properties db;
	public static String title;
	public static String procDB;
	static
	{
		try
		{
			config = PropertyLoader.loadPropValues("config.properties");
			db = PropertyLoader.loadPropValues("db.properties");
			
			title = config.getProperty("title");
			procDB = db.getProperty("findcountbyid");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	

}
