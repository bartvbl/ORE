package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.resources.ResourceCache;
import orre.scene.Scene;

public abstract class GameState implements AbstractGameState {
	public static final int MINIMUM_COMTINUOUS_THREAD_SLEEP_TIME = 10;
	protected Scene sceneGraph;
	protected ResourceCache resourceCache;
	
	protected final GameMain main;
	protected final GlobalEventDispatcher globalEventDispatcher;
	
	public GameState(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache)
	{
		this.main = main;
		this.globalEventDispatcher = eventDispatcher;
		this.resourceCache = cache;
	}

}
