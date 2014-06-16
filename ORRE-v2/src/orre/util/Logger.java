package orre.util;

public class Logger {
	public static enum LogType {MESSAGE, WARNING, ERROR, CRITICAL};
	
	public static void createLog()
	{
		
	}
	
	public static void log(String message, LogType messageType)
	{
		if(messageType != LogType.MESSAGE) {
			System.err.println(messageType + ": " + message);
		} else {
			System.out.println(messageType + ": " + message);
		}
	}
}
