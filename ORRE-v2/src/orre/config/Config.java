package orre.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public static void setConfigValue(ConfigKey key, String value) {
		if(!configFileIsLoaded) {
			loadConfigValues();
		}
		configValues.setProperty(key.toString(), value);
		storeConfigValues();
	}
	
	private static void storeConfigValues() {
		try {
			configValues.store(new FileWriter(configFileLocation), "");
		} catch (FileNotFoundException e) {
			System.out.println("failed to store config file!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("failed to store config file!");
			e.printStackTrace();
		}
	}
	
	private static void loadConfigValues() {
		try {
			configValues.load(new FileReader(configFileLocation));
		} catch (FileNotFoundException e) {
			System.out.println("failed to load config file!");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("failed to load config file!");
			e.printStackTrace();
		}
	}
}
