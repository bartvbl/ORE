package orre.core;

import java.util.HashMap;

import org.lwjgl.opengl.Display;

import orre.events.ConcurrentEventDispatcher;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.EventHandler;
import orre.events.GlobalEventType;
import orre.gameStates.*;
import orre.gl.RenderUtils;
import orre.threads.CleanupThread;


public class GameMain implements EventHandler{
	private boolean gameIsRunning = true;
	private long frameNumber = 0;
	private AbstractGameState currentGameState = null;
	private GlobalEventDispatcher globalEventDispatcher;
	private HashMap<GameStateName, AbstractGameState> stateMap;
	
	public GameMain() 
	{
		this.globalEventDispatcher = new GlobalEventDispatcher();
		this.globalEventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
	}
	
	public void mainLoop()
	{
		while(!Display.isCloseRequested() && gameIsRunning)
		{
			RenderUtils.newFrame();
			this.currentGameState.executeFrame(this.frameNumber);
			this.frameNumber++;
			Display.update();
			Display.sync(60);
		}
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
		AbstractGameState stateToSet = this.stateMap.get(newState);
		this.currentGameState = stateToSet;
		stateToSet.set();
	}
	
	public void initialize()
	{
		GameWindow.create();
		HashMap<GameStateName, AbstractGameState> stateMap = GameStateInitializer.initializeGameStates(this, this.globalEventDispatcher);
		this.stateMap = stateMap;
		this.setGameState(GameStateName.STARTUP_LOADING);
	}

	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType.equals(GlobalEventType.CHANGE_GAME_STATE))
		{
			if((event.getEventParameterObject() == null) || !(event.getEventParameterObject() instanceof GameStateName))
			{
				return;
			}
			this.setGameState((GameStateName) event.getEventParameterObject());
		}
	}
}
