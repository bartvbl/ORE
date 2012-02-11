package orre.core;

import orre.events.EventDispatcher;
import orre.gameStates.*;

public class GameStateInitializer {
	public static AbstractGameState initializeGameStates(GameMain main, EventDispatcher eventDispatcher)
	{
		SequencableGameState startupLoader = new StartupLoader(main, eventDispatcher, GameState.State.STARTUP_LOADING);
		startupLoader.setNextState(GameState.State.MAIN_MENU);
		new MainMenu(main, eventDispatcher, GameState.State.MAIN_MENU);
		new PauseMenu(main, eventDispatcher, GameState.State.PAUSE_MENU);
		new GameRunning(main, eventDispatcher, GameState.State.GAME_RUNNING);
		
		return startupLoader;
	}
}
