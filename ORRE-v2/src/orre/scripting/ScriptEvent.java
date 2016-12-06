package orre.scripting;

import java.util.HashMap;

public class ScriptEvent {
	public final String type;
	public final HashMap<String, String> parameters;

	public ScriptEvent(String type, HashMap<String, String> parameters) {
		this.type = type;
		this.parameters = parameters;
	}
}
