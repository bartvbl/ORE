package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gui.DefaultLoadingScreen;
import orre.resources.ResourceCache;
import orre.resources.ResourceLoader;
import orre.resources.ResourceService;

public class StartupLoader extends LoadingScreen {

	public StartupLoader(GameMain main, GlobalEventDispatcher eventDispatcher, GameStateName stateName, ResourceService resourceService) {
		super(main, eventDispatcher, stateName, resourceService);
		this.setLoadingScreen(new DefaultLoadingScreen(resourceService));
	}

}
