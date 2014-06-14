package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gui.DefaultLoadingScreen;
import orre.resources.ResourceCache;
import orre.resources.ResourceLoader;

public class StartupLoader extends LoadingScreen {

	public StartupLoader(GameMain main, GlobalEventDispatcher eventDispatcher, GameStateName stateName, ResourceCache cache, ResourceLoader loader) {
		super(main, eventDispatcher, stateName, cache, loader);
		this.setLoadingScreen(new DefaultLoadingScreen());
	}

}
