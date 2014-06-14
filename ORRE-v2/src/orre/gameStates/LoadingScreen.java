package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEventDispatcher;
import orre.gui.LoadingScreenDrawer;
import orre.resources.ResourceCache;
import orre.resources.ResourceLoader;

public class LoadingScreen extends SequencableGameState {

	private ResourceLoader resourceLoader;

	public LoadingScreen(GameMain main, GlobalEventDispatcher globalEventDispatcher, GameStateName stateName, ResourceCache cache, ResourceLoader loader) {
		super(main, globalEventDispatcher, stateName, cache);
		this.resourceLoader = loader;
	}
	
	@Override
	public void set() {}
	
	@Override
	public void unset() {}

	@Override
	public void executeFrame(long frameNumber) {
		this.resourceLoader.update();
		if(this.resourceLoader.isFinished())
		{
			this.resourceLoader = null;
			this.finish();
		}
		return;
	}
	
	protected void setLoadingScreen(LoadingScreenDrawer screenDrawer)
	{
		this.resourceLoader.setLoadingScreen(screenDrawer);
	}
	
}
