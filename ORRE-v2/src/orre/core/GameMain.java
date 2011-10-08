package orre.core;

import java.util.ArrayList;

import orre.events.EventDispatcher;
import orre.modules.Module;
import orre.states.*;
import orre.util.FlexibleFrameTimer;


public class GameMain extends EventDispatcher{
	private boolean gameIsRunning = true;
	private long frameNumber = 0;
	private GameState currentGameState = null;
	private FlexibleFrameTimer frameTimer = new FlexibleFrameTimer(60);
	private ArrayList<GameState> gameStates = new ArrayList<GameState>();
	
	public GameMain() 
	{
		
	}

	public void run()
	{
		this.mainLoop();
	}
	
	private void mainLoop()
	{
		while(gameIsRunning)
		{
			this.currentGameState.tick(this.frameNumber);
			this.frameNumber++;
			frameTimer.sleep();
		}
	}
	
	public long getFrameNumber()
	{
		return this.frameNumber;
	}
	
	public synchronized void setGameState(int newStateID)
	{
		GameState newState = this.gameStates.get(newStateID);
		if(newState != null)
		{
			if(this.currentGameState != null)
			{
				this.currentGameState.unset();
			}
			this.currentGameState = newState;
			newState.set();
		}
	}
	
	public void initialize()
	{
		this.gameStates.add(GameState.STARTUP, 		new GameState_Startup());
		this.gameStates.add(GameState.MAIN_MENU, 	new GameState_MainMenu());
		this.gameStates.add(GameState.PAUSE_MENU, 	new GameState_PauseMenu());
		this.gameStates.add(GameState.GAME, 		new GameState_GameRunning());
		
		this.setGameState(GameState.STARTUP);
	}
}
