package orre.resources;

import orre.events.EventHandler;
import orre.events.GlobalEvent;
import orre.events.GlobalEventDispatcher;
import orre.events.GlobalEventType;

public class ResourceService implements EventHandler {

	private final ResourceCache resourceCache;
	private final ResourceLoader resourceLoader;

	public ResourceService(GlobalEventDispatcher globalEventDispatcher) {
		this.resourceCache = new ResourceCache();
		this.resourceLoader = new ResourceLoader(resourceCache);
		globalEventDispatcher.addEventListener(this, GlobalEventType.REGISTER_RESOURCE_TYPE_LOADER);
		globalEventDispatcher.addEventListener(this, GlobalEventType.ENQUEUE_LOADING_ITEM);
	}
	

	@Override
	public void handleEvent(GlobalEvent<?> event) {
		if(event.eventType == GlobalEventType.REGISTER_RESOURCE_TYPE_LOADER) {
			if(!(event.getEventParameterObject() instanceof ResourceTypeLoader))
			{
				throw new RuntimeException("Did not receive a parameter of type ResourceTypeLoader.");
			}
			ResourceTypeLoader loader = (ResourceTypeLoader) event.getEventParameterObject();
			resourceLoader.registerResourceLoader(loader);
		} else if(event.eventType == GlobalEventType.ENQUEUE_LOADING_ITEM) {
			if(!(event.getEventParameterObject() instanceof Resource))
			{
				throw new RuntimeException("Did not receive a parameter of type Resource.");
			}
			Resource resource = (Resource) event.getEventParameterObject();
			resourceLoader.enqueueResource(resource);
		} else if(event.eventType == GlobalEventType.REGISTER_AVAILABLE_RESOURCE_LIST) {
			if(!(event.getEventParameterObject() instanceof Resource)) {
				throw new RuntimeException("Requires a parameter object of type Resource");
			}
			Resource resource = (Resource) event.getEventParameterObject();
			resourceLoader.forceLoadResource_Blocking(resource);
		}
	}

	public Resource getResource(Enum<?> resourceType, String resourceName) {
		return resourceCache.getResource(resourceType, resourceName);
	}

	public void update() {
		this.resourceLoader.update();
	}

	public boolean isCurrentQueueEmpty() {
		return false;
	}

}
