package orre.core;

import java.util.HashMap;

import orre.events.GlobalEventDispatcher;
import orre.gameStates.*;
import orre.resources.ResourceCache;
import orre.resources.ResourceLoader;

public class GameStateInitializer {
	public static HashMap<GameStateName, AbstractGameState> initializeGameStates(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceCache cache, ResourceLoader loader)
	{
		HashMap<GameStateName, AbstractGameState> stateMap = new HashMap<GameStateName, AbstractGameState>();

		StartupIntro intro = new StartupIntro(main, eventDispatcher, cache);
		intro.setNextState(GameStateName.STARTUP_LOADING);
		stateMap.put(GameStateName.STARTUP_INTRO, intro);
		
		
		SequencableGameState startupLoader = new StartupLoader(main, eventDispatcher, GameStateName.STARTUP_LOADING, cache, loader);
		startupLoader.setNextState(GameStateName.MAIN_MENU);
		stateMap.put(GameStateName.STARTUP_LOADING, startupLoader);
		
		SequencableGameState gameLoader = new GameLoader(main, eventDispatcher, GameStateName.GAME_LOADING, cache, loader);
		gameLoader.setNextState(GameStateName.GAME_RUNNING);
		stateMap.put(GameStateName.GAME_LOADING, gameLoader);
		
		
		stateMap.put(GameStateName.MAIN_MENU, new MainMenu(main, eventDispatcher, cache));
		stateMap.put(GameStateName.PAUSE_MENU, new PauseMenu(main, eventDispatcher, cache));
		stateMap.put(GameStateName.GAME_RUNNING, new GameRunning(main, eventDispatcher, cache));
		
		return stateMap;
	}
}
