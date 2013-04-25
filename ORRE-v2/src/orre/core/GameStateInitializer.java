package orre.core;

import java.util.HashMap;

import orre.events.GlobalEventDispatcher;
import orre.gameStates.*;

public class GameStateInitializer {
	public static HashMap<GameStateName, AbstractGameState> initializeGameStates(GameMain main, GlobalEventDispatcher eventDispatcher)
	{
		HashMap<GameStateName, AbstractGameState> stateMap = new HashMap<GameStateName, AbstractGameState>();
		
		SequencableGameState startupLoader = new StartupLoader(main, eventDispatcher, GameStateName.STARTUP_LOADING);
		startupLoader.setNextState(GameStateName.MAIN_MENU);
		stateMap.put(GameStateName.STARTUP_LOADING, startupLoader);
		
		SequencableGameState gameLoader = new GameLoader(main, eventDispatcher, GameStateName.GAME_LOADING);
		gameLoader.setNextState(GameStateName.GAME_RUNNING);
		stateMap.put(GameStateName.GAME_LOADING, gameLoader);
		
		stateMap.put(GameStateName.MAIN_MENU, new MainMenu(main, eventDispatcher, GameStateName.MAIN_MENU));
		stateMap.put(GameStateName.PAUSE_MENU, new PauseMenu(main, eventDispatcher, GameStateName.PAUSE_MENU));
		stateMap.put(GameStateName.GAME_RUNNING, new GameRunning(main, eventDispatcher, GameStateName.GAME_RUNNING));
		
		return stateMap;
	}
}
