package orre.core;

import java.util.HashMap;

import orre.events.EventDispatcher;
import orre.gameStates.*;

public class GameStateInitializer {
	public static HashMap<GameState.State, AbstractGameState> initializeGameStates(GameMain main, EventDispatcher eventDispatcher)
	{
		HashMap<GameState.State, AbstractGameState> stateMap = new HashMap<GameState.State, AbstractGameState>();
		
		SequencableGameState startupLoader = new StartupLoader(main, eventDispatcher, GameState.State.STARTUP_LOADING);
		startupLoader.setNextState(GameState.State.MAIN_MENU);
		stateMap.put(GameState.State.STARTUP_LOADING, startupLoader);
		
		stateMap.put(GameState.State.MAIN_MENU, new MainMenu(main, eventDispatcher, GameState.State.MAIN_MENU));
		stateMap.put(GameState.State.PAUSE_MENU, new PauseMenu(main, eventDispatcher, GameState.State.PAUSE_MENU));
		stateMap.put(GameState.State.GAME_RUNNING, new GameRunning(main, eventDispatcher, GameState.State.GAME_RUNNING));
		
		return stateMap;
	}
}
