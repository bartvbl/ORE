package orre.resources;

import java.util.HashMap;

import orre.events.EventHandler;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;
import orre.gl.renderer.RenderState;
import orre.gui.LoadingScreenDrawer;
import orre.resources.loaders.AnimationLoader;
import orre.resources.loaders.ConfigLoader;
import orre.resources.loaders.LXFMLLoader;
import orre.resources.loaders.MenuLoader;
import orre.resources.loaders.ModelLoader;
import orre.resources.loaders.ResourceListFileParser;
import orre.resources.loaders.ScriptLoader;
import orre.resources.loaders.ShaderLoader;
import orre.resources.loaders.SoundLoader;
import orre.resources.loaders.TextureLoader;

public class ResourceLoader implements EventHandler {
	
	private ProgressTracker progressTracker;
	private ResourceQueue resourceQueue;
	private ResourceFinalizer resourceFinalizer;
	private boolean hasKickedOffLoading = false;
	
	//static to allow multiple ResourceLoaders to exist that share registered loaders.
	private static final HashMap<Enum<?>, ResourceTypeLoader> loaders = new HashMap<Enum<?>, ResourceTypeLoader>();
	
	static {
		//initialise the loaders map when this class is first loaded.
		loaders.put(ResourceType.animation, new AnimationLoader());
		loaders.put(ResourceType.texture, new TextureLoader());
		loaders.put(ResourceType.model, new ModelLoader());
		loaders.put(ResourceType.sound, new SoundLoader());
		loaders.put(ResourceType.resourceList, new ResourceListFileParser());
		loaders.put(ResourceType.script, new ScriptLoader());
		loaders.put(ResourceType.shader, new ShaderLoader());
		loaders.put(ResourceType.config, new ConfigLoader());
		loaders.put(ResourceType.menu, new MenuLoader());
		loaders.put(ResourceType.lxfmlModel, new LXFMLLoader());
	}
	
	public ResourceLoader(ResourceCache cache, GlobalEventDispatcher globalEventDispatcher)
	{
		this.progressTracker = new ProgressTracker();
		this.resourceQueue = new ResourceQueue(this.progressTracker, loaders);
		this.resourceFinalizer = new ResourceFinalizer(this.resourceQueue, cache);
		globalEventDispatcher.addEventListener(this, GlobalEventType.REGISTER_RESOURCE_TYPE_LOADER);
		globalEventDispatcher.addEventListener(this, GlobalEventType.ENQUEUE_LOADING_ITEM);
	}

	public void update()
	{
		if(!hasKickedOffLoading) {
			this.resourceQueue.startLoading();
			hasKickedOffLoading = true;
		}
		this.resourceFinalizer.doFinalizations();
		
	}
	
	public boolean isFinished() {
		return this.progressTracker.isFinished() && this.resourceQueue.finalizableQueueIsEmpty();
	}

	@Override
	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType == GlobalEventType.REGISTER_RESOURCE_TYPE_LOADER) {
			if(!(event.getEventParameterObject() instanceof ResourceTypeLoader))
			{
				throw new RuntimeException("Did not receive a parameter of type ResourceTypeLoader.");
			}
			ResourceTypeLoader loader = (ResourceTypeLoader) event.getEventParameterObject();
			loaders.put(loader.getResourceType(), loader);
		} else if(event.eventType == GlobalEventType.ENQUEUE_LOADING_ITEM) {
			if(!(event.getEventParameterObject() instanceof UnloadedResource))
			{
				throw new RuntimeException("Did not receive a parameter of type UnloadedResource.");
			}
			UnloadedResource resource = (UnloadedResource) event.getEventParameterObject();
			this.resourceQueue.enqueueNodeForLoading(resource);
		}
	}

	public double getProgress() {
		return this.progressTracker.getProgress();
	}

	
}
