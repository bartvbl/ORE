package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gui.DefaultLoadingScreen;
import orre.resources.ResourceCache;
import orre.resources.ResourceLoader;

public class GameLoader extends LoadingScreen {

	public GameLoader(GameMain main, GlobalEventDispatcher eventDispatcher, GameStateName stateName, ResourceCache cache, ResourceLoader loader) {
		super(main, eventDispatcher, stateName, cache, loader);
		this.setLoadingScreen(new DefaultLoadingScreen());
	}

}
