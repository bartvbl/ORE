package orre.input;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class KeyBindings {
	private static final HashMap<KeyType, ArrayList<String>> bindings = new HashMap<KeyType, ArrayList<String>>();

	public static void bind(final KeyType type, final String command) {
		if(!bindings.containsKey(type)) {
			bindings.put(type, new ArrayList<String>());
		}
		bindings.get(type).add(command);
	}

	public static void unbind(final KeyType type, final String command) {
		final ArrayList<String> commandList = bindings.get(type);
		commandList.remove(command);
		if(commandList.isEmpty()) {
			bindings.remove(type);
		}
	}

	static ArrayList<String> getBindings(final KeyType type) {
		return bindings.get(type);
	}

	public static void unbindAll() {
		bindings.clear();
	}

}
