package orre.core;

import java.util.HashMap;

import org.lwjgl.opengl.Display;

import orre.api.GameSettings;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.EventHandler;
import orre.events.GlobalEventType;
import orre.gameStates.*;
import orre.gameWorld.core.GameWorld;
import orre.gl.RenderUtils;
import orre.resources.ResourceCache;
import orre.resources.ResourceLoader;
import orre.scripting.ScriptInterpreter;
import orre.util.Logger;

public class GameMain implements EventHandler{
	private boolean gameIsRunning = true;
	private long frameNumber = 0;
	private AbstractGameState currentGameState = null;
	private GlobalEventDispatcher globalEventDispatcher;
	private HashMap<GameStateName, AbstractGameState> stateMap;
	
	private final String gameName;
	private final ResourceCache resourceCache;
	private final ResourceLoader resourceLoader;
	private final ScriptInterpreter scriptInterpreter;
	private final GameSettings settings;
	
	public GameMain(GameSettings settings) 
	{
		new Logger();
		this.gameName = settings.gameName;
		this.settings = settings;
		GameWorld.setPropertyTypeProvider(settings.propertyTypeProvider);
		this.globalEventDispatcher = new GlobalEventDispatcher();
		this.globalEventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
		this.resourceCache = new ResourceCache();
		this.resourceLoader = new ResourceLoader(resourceCache, globalEventDispatcher);
		this.scriptInterpreter = new ScriptInterpreter(globalEventDispatcher);
	}
	
	public void mainLoop()
	{
		while(!Display.isCloseRequested() && gameIsRunning)
		{
			RenderUtils.newFrame();
			RenderUtils.set3DMode();
			this.currentGameState.executeFrame(this.frameNumber);
			this.frameNumber++;
			Display.update();
			Display.sync(60);
		}
		this.currentGameState.unset();
		System.exit(0);
	}

	public long getFrameNumber()
	{
		return this.frameNumber;
	}
	
	private void setGameState(GameStateName newState)
	{
		if(this.currentGameState != null)
		{
			this.currentGameState.unset();
		}
		RenderUtils.resetSettings();
		AbstractGameState stateToSet = this.stateMap.get(newState);
		this.currentGameState = stateToSet;
		stateToSet.set();
	}
	
	public void initialize()
	{
		GameWindow.create(gameName);
		this.scriptInterpreter.init();
		HashMap<GameStateName, AbstractGameState> stateMap = GameStateInitializer.initializeGameStates(this, this.globalEventDispatcher, resourceCache, resourceLoader, scriptInterpreter, settings);
		this.stateMap = stateMap;
		this.setGameState(GameStateName.STARTUP_LOADING);
	}

	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType == GlobalEventType.CHANGE_GAME_STATE)
		{
			if((event.getEventParameterObject() == null) || !(event.getEventParameterObject() instanceof GameStateName))
			{
				return;
			}
			this.setGameState((GameStateName) event.getEventParameterObject());
		}
	}
	
	public void api_dispatchGlobalEvent(GlobalEvent<?> event) {
		this.globalEventDispatcher.dispatchEvent(event);
	}
}
