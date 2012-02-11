package orre.core;

import orre.events.EventDispatcher;
import orre.gameStates.*;

public class GameStateInitializer {
	public static AbstractGameState initializeGameStates(GameMain main, EventDispatcher eventDispatcher)
	{
		//TODO: next state must be a State enum value. find a way to get that properly into the GameMain..
		SequencableGameState startupLoader = new StartupLoader(main, eventDispatcher, GameState.State.STARTUP_LOADING);
		MainMenu mainMenu = new MainMenu(main, eventDispatcher, GameState.State.MAIN_MENU);
		startupLoader.setNextState(mainMenu);
		new PauseMenu(main, eventDispatcher, GameState.State.PAUSE_MENU);
		new GameRunning(main, eventDispatcher, GameState.State.GAME_RUNNING);
		
		return startupLoader;
	}
}
