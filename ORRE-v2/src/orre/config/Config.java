package orre.config;

import java.util.HashMap;
import java.util.Properties;

public class Config {
	private static final Properties configMap = new Properties();
	
	public static void set(String key, String value) {
		configMap.put(key, value);
	}
	
	public static void setMany(HashMap<String, String> keys) {
		configMap.putAll(keys);
	}
	
	public static String get(ConfigKey key) {
		return configMap.getProperty(key.toString());
	}
	
	public static String get(String key) {
		return configMap.getProperty(key);
	}
}
