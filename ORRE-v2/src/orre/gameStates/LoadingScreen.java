package orre.gameStates;

import orre.core.GameMain;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.EventHandler;
import orre.events.GlobalEventType;
import orre.gl.texture.Texture;
import orre.gui.LoadingScreenDrawer;
import orre.resources.FileToLoad;
import orre.resources.ResourceCache;
import orre.resources.ResourceFile;
import orre.resources.ResourceLoader;
import orre.resources.loaders.TextureLoader;

public class LoadingScreen extends SequencableGameState implements EventHandler {

	private ResourceLoader resourceLoader;
	private final GlobalEventType enqueueResourceEventType;

	public LoadingScreen(GameMain main, GlobalEventDispatcher globalEventDispatcher, GlobalEventType eventType, GameState.State stateName) {
		super(main, globalEventDispatcher, stateName);
		globalEventDispatcher.addEventListener(this, eventType);
		this.enqueueResourceEventType = eventType;
		this.resourceLoader = new ResourceLoader();
	}
	
	public void set() {
		if(this.resourceLoader == null)
		{
			this.resourceLoader = new ResourceLoader();
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
	
	protected void enqueueResourceFileToBeLoaded(String src, String name, ResourceFile resourceListFile, ResourceCache destinationCache)
	{
		this.resourceLoader.enqueueResourceFileToBeLoaded(src, name, resourceListFile, destinationCache);
	}
	
	protected void setLoadingScreen(LoadingScreenDrawer screenDrawer)
	{
		this.resourceLoader.setLoadingScreen(screenDrawer);
	}
	
	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType.equals(this.enqueueResourceEventType))
		{
			if(!(event.getEventParameterObject() instanceof FileToLoad))
			{
				return;
			}
			FileToLoad file = (FileToLoad) event.getEventParameterObject();
			this.enqueueResourceFileToBeLoaded(file.getPath(), "mainMenuLoadingScreenTexture", file.fileType, file.destinationCache);
		}
	}

}
