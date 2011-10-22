package orre.core;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

import orre.events.EventDispatcher;
import orre.gl.RenderUtils;
import orre.states.*;


public class GameMain extends EventDispatcher{
	private boolean gameIsRunning = true;
	private long frameNumber = 0;
	private GameState currentGameState = null;
	private ArrayList<GameState> gameStates = new ArrayList<GameState>();
	private GameWindow mainWindow;
	
	public GameMain() 
	{
		mainWindow = new GameWindow();
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
			this.currentGameState.tick(this.frameNumber);
			this.frameNumber++;
			Display.update();
			Display.sync(60);
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
		this.mainWindow.create();
		
		this.gameStates.add(GameState.STARTUP, 		new GameState_Startup());
		this.gameStates.add(GameState.MAIN_MENU, 	new GameState_MainMenu());
		this.gameStates.add(GameState.PAUSE_MENU, 	new GameState_PauseMenu());
		this.gameStates.add(GameState.GAME, 		new GameState_GameRunning());
		
		this.setGameState(GameState.STARTUP);
	}
}
