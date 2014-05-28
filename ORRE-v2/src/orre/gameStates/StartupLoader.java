package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gui.DefaultLoadingScreen;
import orre.resources.ResourceCache;

public class StartupLoader extends LoadingScreen {

	public StartupLoader(GameMain main, GlobalEventDispatcher eventDispatcher, GameStateName stateName, ResourceCache cache) {
		super(main, eventDispatcher, GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, stateName, cache);
		this.setLoadingScreen(new DefaultLoadingScreen());
	}

}
