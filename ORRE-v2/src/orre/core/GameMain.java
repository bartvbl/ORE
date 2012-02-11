package orre.core;

import org.lwjgl.opengl.Display;

import orre.events.ConcurrentEventDispatcher;
import orre.events.Event;
import orre.events.EventDispatcher;
import orre.events.EventHandler;
import orre.events.GlobalEventType;
import orre.gameStates.*;
import orre.gl.RenderUtils;


public class GameMain extends ConcurrentEventDispatcher implements EventHandler{
	private boolean gameIsRunning = true;
	private long frameNumber = 0;
	private AbstractGameState currentGameState = null;
	private EventDispatcher globalEventDispatcher;
	
	public GameMain() 
	{
		this.globalEventDispatcher = new EventDispatcher();
		this.globalEventDispatcher.addEventListener(this, GlobalEventType.CHANGE_GAME_STATE);
	}

	public void run()
	{
		this.mainLoop();
	}
	
	private void mainLoop()
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
	
	private void setGameState(AbstractGameState newState)
	{
		if(this.currentGameState != null)
		{
			this.currentGameState.unset();
		}
		this.currentGameState = newState;
		newState.set();
	}
	
	public void initialize()
	{
		GameWindow.create();
		AbstractGameState startState = GameStateInitializer.initializeGameStates(this, this.globalEventDispatcher);
		System.out.println("initializing");
		this.setGameState(startState);
	}

	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(GlobalEventType.CHANGE_GAME_STATE))
		{
			if((event.getEventParameterObject() == null) || !(event.getEventParameterObject() instanceof AbstractGameState))
			{
				return;
			}
			this.setGameState((AbstractGameState) event.getEventParameterObject());
		}
	}
}
