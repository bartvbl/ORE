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

public class LoadingScreen extends SequencableGameState implements EventHandler {

	private ResourceLoader resourceLoader;
	private final GlobalEventType enqueueResourceEventType;

	public LoadingScreen(GameMain main, GlobalEventDispatcher globalEventDispatcher, GlobalEventType eventType, GameStateName stateName, ResourceCache cache) {
		super(main, globalEventDispatcher, stateName, cache);
		globalEventDispatcher.addEventListener(this, eventType);
		this.enqueueResourceEventType = eventType;
		this.resourceLoader = new ResourceLoader(cache);
	}
	
	public void set() {
		if(this.resourceLoader == null)
		{
			this.resourceLoader = new ResourceLoader(resourceCache);
		}
	}
	
	public void unset() {
		this.resourceLoader = null;
		
	}

	public void executeFrame(long frameNumber) {
		this.resourceLoader.update();
		if(this.resourceLoader.isFinished())
		{
			this.resourceLoader = null;
			this.finish();
		}
		return;
	}
	
	protected void enqueueResourceFileToBeLoaded(UnloadedResource resource)
	{
		this.resourceLoader.enqueueResourceFileToBeLoaded(resource);
	}
	
	protected void setLoadingScreen(LoadingScreenDrawer screenDrawer)
	{
		this.resourceLoader.setLoadingScreen(screenDrawer);
	}
	
	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType.equals(this.enqueueResourceEventType))
		{
			if(!(event.getEventParameterObject() instanceof UnloadedResource))
			{
				return;
			}
			UnloadedResource file = (UnloadedResource) event.getEventParameterObject();
			this.enqueueResourceFileToBeLoaded(file);
		}
	}

}
