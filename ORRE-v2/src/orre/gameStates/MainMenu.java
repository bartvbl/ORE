package orre.gameStates;

import orre.core.GameMain;
import orre.events.Event;
import orre.events.EventDispatcher;
import orre.events.GlobalEventType;
import orre.gl.RenderUtils;
import orre.resources.FileToLoad;
import orre.resources.ResourceFile;

public class MainMenu extends GameState {
	public MainMenu(GameMain main, EventDispatcher eventDispatcher, GameState.State stateName) {
		super(main, eventDispatcher, stateName);
		FileToLoad mainCache = new FileToLoad(ResourceFile.RESOURCE_LIST_FILE, this.resourceCache, "res/reslist.xml");
		eventDispatcher.dispatchEvent(new Event<FileToLoad>(GlobalEventType.ENQUEUE_STARTUP_LOADING_ITEM, mainCache));
	}
	public void executeFrame(long frameNumber) {
		RenderUtils.set3DMode();
	}
	@Override
	public void set() {
		
	}
	@Override
	public void unset() {
		
		
	}



}
