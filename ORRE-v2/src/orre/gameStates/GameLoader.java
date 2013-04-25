package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gui.DefaultLoadingScreen;

public class GameLoader extends LoadingScreen {

	public GameLoader(GameMain main, GlobalEventDispatcher eventDispatcher, GameStateName stateName) {
		super(main, eventDispatcher, GlobalEventType.ENQUEUE_GAME_LOADING_ITEM, stateName);
		this.setLoadingScreen(new DefaultLoadingScreen());
	}

}
