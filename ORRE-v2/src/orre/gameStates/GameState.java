package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.resources.ResourceService;
import orre.scene.Scene;
import orre.scripting.ScriptInterpreter;

public abstract class GameState implements AbstractGameState {
	public static final int MINIMUM_COMTINUOUS_THREAD_SLEEP_TIME = 10;
	protected Scene sceneGraph;
	protected ResourceService resourceService;
	
	protected final GameMain main;
	protected final GlobalEventDispatcher globalEventDispatcher;
	protected final ScriptInterpreter interpreter;
	
	public GameState(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceService resourceService, ScriptInterpreter interpreter)
	{
		this.main = main;
		this.globalEventDispatcher = eventDispatcher;
		this.resourceService = resourceService;
		this.interpreter = interpreter;
	}

}
