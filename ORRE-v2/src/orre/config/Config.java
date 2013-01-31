package orre.config;

import java.util.Properties;

public class Config {
	private static final String configFileLocation = "res/config/config.cfg";
	
	private static final Properties configValues = new Properties();
	private static boolean configFileIsLoaded = false;
	
	public static String getConfigValue(ConfigKey key) {
		if(!configFileIsLoaded) {
			loadConfigValues();
		}
		return configValues.getProperty(key.toString());
	}
	
	public static void setConfigValue() {
		
	}
	
	private static void loadConfigValues() {
		
	}
}
