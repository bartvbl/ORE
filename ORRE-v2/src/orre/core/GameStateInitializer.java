package orre.core;

import java.util.HashMap;

import orre.api.GameSettings;
import orre.events.GlobalEventDispatcher;
import orre.gameStates.*;
import orre.resources.ResourceCache;
import orre.resources.ResourceLoader;
import orre.resources.ResourceService;
import orre.scripting.ScriptInterpreter;

public class GameStateInitializer {
	public static HashMap<GameStateName, AbstractGameState> initializeGameStates(GameMain main, GlobalEventDispatcher eventDispatcher, ResourceService resourceService, ScriptInterpreter interpreter, GameSettings settings)
	{
		HashMap<GameStateName, AbstractGameState> stateMap = new HashMap<GameStateName, AbstractGameState>();

		StartupIntro intro = new StartupIntro(main, eventDispatcher, resourceService);
		intro.setNextState(GameStateName.STARTUP_LOADING);
		stateMap.put(GameStateName.STARTUP_INTRO, intro);
		
		
		SequencableGameState startupLoader = new StartupLoader(main, eventDispatcher, GameStateName.STARTUP_LOADING, resourceService);
		startupLoader.setNextState(GameStateName.MAIN_MENU);
		stateMap.put(GameStateName.STARTUP_LOADING, startupLoader);
		
		SequencableGameState gameLoader = new GameLoader(main, eventDispatcher, GameStateName.GAME_LOADING, resourceService);
		gameLoader.setNextState(GameStateName.GAME_RUNNING);
		stateMap.put(GameStateName.GAME_LOADING, gameLoader);
		
		
		stateMap.put(GameStateName.MAIN_MENU, new MainMenu(main, eventDispatcher, resourceService, interpreter));
		stateMap.put(GameStateName.PAUSE_MENU, new PauseMenu(main, eventDispatcher, resourceService, interpreter));
		stateMap.put(GameStateName.GAME_RUNNING, new GameRunning(main, eventDispatcher, resourceService, interpreter));
		
		return stateMap;
	}
}
