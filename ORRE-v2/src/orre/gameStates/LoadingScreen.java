package orre.gameStates;

import orre.core.GameMain;
import orre.events.Event;
import orre.events.EventDispatcher;
import orre.events.EventHandler;
import orre.gl.texture.Texture;
import orre.gui.LoadingScreenDrawer;
import orre.resources.FileToLoad;
import orre.resources.ResourceCache;
import orre.resources.ResourceFile;
import orre.resources.ResourceLoader;
import orre.resources.loaders.TextureLoader;

public class LoadingScreen extends SequencableGameState implements EventHandler {

	private ResourceLoader resourceLoader;
	private final String enqueueResourceEventType;

	public LoadingScreen(GameMain main, EventDispatcher globalEventDispatcher, String enqueueResourceEventType, GameState.State stateName) {
		super(main, globalEventDispatcher, stateName);
		globalEventDispatcher.addEventListener(this, enqueueResourceEventType);
		this.enqueueResourceEventType = enqueueResourceEventType;
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
	
	protected void enqueueResourceFileToBeLoaded(String src, ResourceFile resourceListFile, ResourceCache destinationCache)
	{
		this.resourceLoader.enqueueResourceFileToBeLoaded(src, resourceListFile, destinationCache);
	}
	
	protected void setLoadingScreen(LoadingScreenDrawer screenDrawer)
	{
		this.resourceLoader.setLoadingScreen(screenDrawer);
	}
	
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(this.enqueueResourceEventType))
		{
			if(!(event.getEventParameterObject() instanceof FileToLoad))
			{
				return;
			}
			FileToLoad file = (FileToLoad) event.getEventParameterObject();
			this.enqueueResourceFileToBeLoaded(file.getPath(), file.fileType, file.destinationCache);
		}
	}

}
