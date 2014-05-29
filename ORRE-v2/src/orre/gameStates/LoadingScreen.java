package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.EventHandler;
import orre.events.GlobalEventType;
import orre.gl.texture.Texture;
import orre.gui.LoadingScreenDrawer;
import orre.resources.UnloadedResource;
import orre.resources.ResourceCache;
import orre.resources.ResourceType;
import orre.resources.ResourceLoader;
import orre.resources.loaders.TextureLoader;

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
