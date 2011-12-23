package orre.gameStates;

import java.util.ArrayList;

import orre.core.GameMain;
import orre.modules.Module;
import orre.resources.ResourceCache;

public class Startup extends GameState {

	public Startup(GameMain main) {
		super(main);
	}
	
	protected void executeLoadingFrame(long frameNumber)
	{
		this.loadingFinished(new ResourceCache());
	}
	
	protected void executeFrame(long frameNumber)
	{
		this.main.setGameState(GameState.MAIN_MENU);
	}

}
