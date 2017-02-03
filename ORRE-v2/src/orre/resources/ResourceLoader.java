package orre.resources;

import java.util.HashMap;

import orre.resources.loaders.AnimationLoader;
import orre.resources.loaders.ConfigLoader;
import orre.resources.loaders.LWSLoader;
import orre.resources.loaders.LXFMLLoader;
import orre.resources.loaders.MenuLoader;
import orre.resources.loaders.ModelLoader;
import orre.resources.loaders.ResourceListFileParser;
import orre.resources.loaders.ScriptLoader;
import orre.resources.loaders.ShaderLoader;
import orre.resources.loaders.SoundLoader;
import orre.resources.loaders.TextureLoader;

public class ResourceLoader {
	
	private ProgressTracker progressTracker;
	private ResourceQueue resourceQueue;
	private ResourceCompleter resourceFinalizer;
	
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
		loaders.put(ResourceType.lwsAnimation, new LWSLoader());
	}
	
	public ResourceLoader(ResourceCache cache)
	{
		this.progressTracker = new ProgressTracker();
		this.resourceQueue = new ResourceQueue(this.progressTracker, loaders);
		this.resourceFinalizer = new ResourceCompleter(this.resourceQueue, this);
	}

	public void update()
	{
		this.resourceFinalizer.completeResources();
	}
	
	public boolean isFinished() {
		return this.progressTracker.isFinished() && this.resourceQueue.finalizableQueueIsEmpty();
	}


	public double getProgress() {
		return this.progressTracker.getProgress();
	}

	public void registerResourceLoader(ResourceTypeLoader loader) {
		loaders.put(loader.getResourceType(), loader);
	}

	public void enqueueResource(Resource resource) {
		resourceQueue.enqueueNodeForLoading(resource);
	}
	
	public ResourceTypeLoader getLoaderFor(Enum<?> resourceType) {
		return loaders.get(resourceType);
	}

	public void forceLoadResource_Blocking(Resource resource) {
	}

	
}
