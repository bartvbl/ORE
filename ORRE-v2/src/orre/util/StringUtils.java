package orre.util;

public class StringUtils {
	public static String stripString(String string)
	{
		string = string.replaceAll("  ", " ");
		return string.trim();
	}
	
	public static String join (String[] words, String concatenator) {
		String newString = "";
		for (int i=0; i<words.length; i++) {
			if ((i+1)>=words.length) {
				newString = newString+words[i];
			}
			else {
				newString = newString+(words[i]+concatenator);
			}
		}
		return newString;
	}
}
