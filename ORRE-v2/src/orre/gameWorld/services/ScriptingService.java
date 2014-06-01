package orre.gameWorld.services;

import orre.scripting.ScriptInterpreter;

public class ScriptingService implements Service {
	private final ScriptInterpreter interpreter;

	public ScriptingService(ScriptInterpreter interpreter) {
		this.interpreter = interpreter;
	}

	@Override
	public void tick() {
		
	}

}
