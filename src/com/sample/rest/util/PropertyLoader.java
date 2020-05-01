package com.sample.rest.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader
{
	static String result = "";
	static InputStream inputStream;
	static Properties propFile;
	public static String getPropValues(String propFileName, String propName) throws IOException
	{
		try
		{
			Properties prop = new Properties();
			inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null)
			{
				prop.load(inputStream);
			}
			else
			{
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			//Date time = new Date(System.currentTimeMillis());
			result = prop.getProperty(propName);
			System.out.println(result);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception: " +e.getMessage() );
		}
		finally
		{
			inputStream.close();
		}
		return result;
	}
	
	public static Properties loadPropValues(String propFileName) throws IOException
	{
		try
		{
			Properties prop = new Properties();
			inputStream = PropertyLoader.class.getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null)
			{
				prop.load(inputStream);
			}
			else
			{
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
			propFile = prop;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception: " +e.getMessage() );
		}
		finally
		{
			inputStream.close();
		}
		return propFile;
	}
	
	
}