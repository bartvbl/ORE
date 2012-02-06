package orre.core;

import orre.events.EventDispatcher;
import orre.gameStates.*;

public class GameStateInitializer {
	public static AbstractGameState initializeGameStates(GameMain main, EventDispatcher eventDispatcher)
	{
		Startup startup = new Startup(main, eventDispatcher);
		new MainMenu(main, eventDispatcher);
		new PauseMenu(main, eventDispatcher);
		new GameRunning(main, eventDispatcher);
		
		return startup;
	}
}
