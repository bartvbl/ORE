package orre.gameStates;

import orre.core.GameMain;
import orre.events.EventDispatcher;
import orre.gui.LoadingScreenDrawer;
import orre.resources.ResourceFile;
import orre.resources.ResourceLoader;

public class LoadingScreen extends SequencableGameState {

	private ResourceLoader resourceLoader;

	public LoadingScreen(GameMain main, EventDispatcher globalEventDispatcher) {
		super(main, globalEventDispatcher);
		
	}

	public void set() {
		
	}

	public void unset() {
		
	}


	public void tick(long frameNumber) {
		this.resourceLoader.update();
		if(this.resourceLoader.isFinished())
		{
			this.finish();
		}
		return;
	}

	protected void executeFrame(long frameNumber) {
		
	}
	
	protected void enqueueResourceFileToBeLoaded(String src, ResourceFile resourceListFile)
	{
		this.resourceLoader.enqueueResourceFileToBeLoaded(src, resourceListFile);
	}
	
	protected void setLoadingScreen(LoadingScreenDrawer screenDrawer)
	{
		this.resourceLoader.setLoadingScreen(screenDrawer);
	}

}
