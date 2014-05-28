package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gui.DefaultLoadingScreen;
import orre.resources.ResourceCache;

public class GameLoader extends LoadingScreen {

	public GameLoader(GameMain main, GlobalEventDispatcher eventDispatcher, GameStateName stateName, ResourceCache cache) {
		super(main, eventDispatcher, GlobalEventType.ENQUEUE_GAME_LOADING_ITEM, stateName, cache);
		this.setLoadingScreen(new DefaultLoadingScreen());
	}

}
