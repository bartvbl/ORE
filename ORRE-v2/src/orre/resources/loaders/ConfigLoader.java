package orre.resources.loaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import orre.config.Config;
import orre.input.KeyBindings;
import orre.input.KeyType;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.util.Logger;
import orre.util.Logger.LogType;

public class ConfigLoader implements ResourceTypeLoader {
	
	@Override
	public Enum<?> getResourceType() {
		return ResourceType.config;
	}

	@Override
	public Finalizable loadResource(Resource source, ResourceQueue queue) throws Exception {
		BufferedReader reader = new BufferedReader(new FileReader(source.fileLocation));
		HashMap<String, String> configMap = new HashMap<String, String>();
		
		int lineNumber = 0;
		while(reader.ready()) {
			lineNumber++;
			parseConfigLine(reader.readLine(), lineNumber, configMap);
		}
		
		Config.setMany(configMap);
		
		reader.close();
		return null;
	}

	private void parseConfigLine(String line, int lineNumber, HashMap<String, String> configMap) {
		line = line.trim();
		if(line.startsWith("#") || line.equals("")) {
			return;
		} else if(line.startsWith("set")) {
			String[] parts = line.split(" ");
			if(parts.length != 3) {
				Logger.log("Invalid usage of set command on line " + lineNumber + ". Correct usage: set <key> <value>", LogType.ERROR);
				return;
			}
			configMap.put(parts[1], parts[2]);
		} else if(line.startsWith("bind")) {
			String[] parts = line.split(" ");
			if(parts.length != 3) {
				Logger.log("Invalid usage of bind command on line " + lineNumber + ". Correct usage: bind <key type> <command string>", LogType.ERROR);
				return;
			}
			try {
				KeyType keyType = KeyType.valueOf(parts[1]);
				KeyBindings.bind(keyType, parts[2]);
			} catch(IllegalArgumentException e) {
				Logger.log("Unknown key type \""+parts[1]+"\" on line " + lineNumber + ".", LogType.ERROR);
				return;
			}
		} else if(line.startsWith("unbind")) {
			if(line.equals("unbind all")) {
				KeyBindings.unbindAll();
				return;
			} else {
				String[] parts = line.split(" ");
				if(parts.length != 3) {
					Logger.log("Invalid usage of unbind command on line " + lineNumber + ". Correct usage: unbind <key type> <command string>", LogType.ERROR);
					return;
				}
				try {
					KeyType keyType = KeyType.valueOf(parts[1]);
					KeyBindings.unbind(keyType, parts[2]);
				} catch(IllegalArgumentException e) {
					Logger.log("Unknown key type \""+parts[1]+"\" on line " + lineNumber + ".", LogType.ERROR);
					return;
				}
			}
		}
	}

}
