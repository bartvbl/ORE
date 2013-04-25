package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gameStates.GameState.State;
import orre.gui.DefaultLoadingScreen;

public class StartupLoader extends LoadingScreen {

	public StartupLoader(GameMain main, GlobalEventDispatcher eventDispatcher, State stateName) {
		super(main, eventDispatcher, GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, stateName);
		this.setLoadingScreen(new DefaultLoadingScreen());
	}

}
