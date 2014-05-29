package orre.threads;

import java.util.HashMap;

import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;
import orre.resources.ResourceType;
import orre.resources.ResourceListFileParser;
import orre.resources.ResourceQueue;
import orre.util.Queue;

//TODO: this should be moved into the regular loading system where the loader throws in all the resources it finds at the entrance of the resource loading pipeline.
public class ResourceFileParsingThread extends Thread{

	private final Queue<UnloadedResource> remainingItemsQueue;
	private final ResourceQueue resourceQueue;
	private final HashMap<ResourceType, ResourceTypeLoader> registeredLoaders;

	public ResourceFileParsingThread(ResourceQueue queue, Queue<UnloadedResource> itemsToLoadQueue, HashMap<ResourceType, ResourceTypeLoader> loaders) {
		this.remainingItemsQueue = itemsToLoadQueue;
		this.resourceQueue = queue;
		this.registeredLoaders = loaders;
	}

	public void run()
	{
		UnloadedResource file = this.remainingItemsQueue.dequeue();
		while(file != null)
		{
			this.parseResourceFile(file);
			file = this.remainingItemsQueue.dequeue();
		}
		this.resourceQueue.startLoading(registeredLoaders);
	}

	private void parseResourceFile(UnloadedResource file) {
		if(file.fileType == ResourceType.RESOURCE_LIST_FILE)
		{
			ResourceListFileParser.parseFile(file, this.resourceQueue);
		} else {
			//any manually enqueued files are just dumped in the resource loading queue. For example; the map file is enqueued separately.
			this.resourceQueue.enqueueNodeForLoading(file);
		}
	}
}
