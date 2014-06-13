package orre.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class KeyBindings {
	private static final HashMap<KeyType, ArrayList<String>> bindings = new HashMap<KeyType, ArrayList<String>>();
	
	public static void bind(KeyType type, String command) {
		if(!bindings.containsKey(type)) {
			bindings.put(type, new ArrayList<String>());
		}
		bindings.get(type).add(command);
	}
	
	public static void unbind(KeyType type, String command) {
		ArrayList<String> commandList = bindings.get(type);
		commandList.remove(command);
		if(commandList.isEmpty()) {
			bindings.remove(type);
		}
	}
	
	static Iterator<String> getBindings(KeyType type) {
		return bindings.get(type).iterator();
	}
	
	public static void unbindAll() {
		bindings.clear();
	}
	
}
