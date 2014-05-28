package orre.threads;

import orre.resources.UnloadedResource;
import orre.resources.ResourceType;
import orre.resources.ResourceListFileParser;
import orre.resources.ResourceQueue;
import orre.util.Queue;

public class ResourceFileParsingThread extends Thread{

	private Queue<UnloadedResource> remainingItemsQueue;
	private ResourceQueue resourceQueue;

	public ResourceFileParsingThread(ResourceQueue queue, Queue<UnloadedResource> itemsToLoadQueue) {
		this.remainingItemsQueue = itemsToLoadQueue;
		this.resourceQueue = queue;
	}

	public void run()
	{
		UnloadedResource file = this.remainingItemsQueue.dequeue();
		while(file != null)
		{
			this.parseResourceFile(file);
			file = this.remainingItemsQueue.dequeue();
		}
		this.resourceQueue.startLoading();
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
