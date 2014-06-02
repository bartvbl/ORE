package orre.gameWorld.services;

import orre.gameWorld.core.GameWorld;
import orre.scripting.ScriptInterpreter;

public class ScriptingService implements Service {
	private final ScriptInterpreter interpreter;

	public ScriptingService(GameWorld world, ScriptInterpreter interpreter) {
		this.interpreter = interpreter;
		//dirty hack to propagate the game state's world instance to the script interpreter.
		interpreter.setCurrentWorld(world);
	}

	@Override
	public void tick() {
		
	}

}
