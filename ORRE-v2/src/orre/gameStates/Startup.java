package orre.gameStates;

import java.util.ArrayList;

import orre.core.GameMain;
import orre.events.EventDispatcher;
import orre.modules.Module;
import orre.resources.ResourceCache;
import orre.resources.ResourceFile;
import orre.scene.Scene;

public class Startup extends GameState {

	public Startup(GameMain main) {
		super(main);
	}
	
	protected void executeFrame(long frameNumber)
	{
		
		this.main.setGameState(GameState.MAIN_MENU);
	}

	protected void unloadState() {
		
	}

	protected void doPreload() {
		this.enqueueResourceFileToBeLoaded("res/reslist.xml", ResourceFile.RESOURCE_LIST_FILE);
	}

	protected void doPostLoad() {
		
	}

}
