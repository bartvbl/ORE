package orre.util;

public class Logger {
	public static enum LogType {MESSAGE, WARNING, ERROR, CRITICAL};
	
	public static void createLog()
	{
		
	}
	
	public static void log(String message, LogType messageType)
	{
		System.out.println(messageType + ": " + message);
	}
}
