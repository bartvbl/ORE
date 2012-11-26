package orre.core;

import java.util.HashMap;

import org.lwjgl.opengl.Display;

import orre.events.ConcurrentEventDispatcher;
import orre.events.Event;
import orre.events.EventDispatcher;
import orre.events.EventHandler;
import orre.events.GlobalEventType;
import orre.gameStates.*;
import orre.gameStates.GameState.State;
import orre.gl.RenderUtils;
import orre.threads.CleanupThread;


public class GameMain extends ConcurrentEventDispatcher implements EventHandler{
	private boolean gameIsRunning = true;
	private long frameNumber = 0;
	private AbstractGameState currentGameState = null;
	private EventDispatcher globalEventDispatcher;
	private HashMap<State, AbstractGameState> stateMap;
	
	public GameMain() 
	{
		this.globalEventDispatcher = new EventDispatcher();
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
	
	private void setGameState(State newState)
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
		HashMap<GameState.State, AbstractGameState> stateMap = GameStateInitializer.initializeGameStates(this, this.globalEventDispatcher);
		this.stateMap = stateMap;
		this.setGameState(GameState.State.STARTUP_LOADING);
	}

	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(GlobalEventType.CHANGE_GAME_STATE))
		{
			if((event.getEventParameterObject() == null) || !(event.getEventParameterObject() instanceof GameState.State))
			{
				return;
			}
			this.setGameState((GameState.State) event.getEventParameterObject());
		}
	}
}
