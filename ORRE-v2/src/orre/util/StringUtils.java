package orre.util;

public class StringUtils {
	public static String stripString(String string)
	{
		string = string.replaceAll("  ", " ");
		return string.trim();
	}
}
