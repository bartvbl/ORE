package orre.threads;

import java.util.HashMap;

import orre.resources.ResourceTypeLoader;
import orre.resources.UnloadedResource;
import orre.resources.Finalizable;
import orre.resources.ProgressTracker;
import orre.resources.ResourceType;
import orre.resources.ResourceQueue;
import orre.util.FatalExceptionHandler;

public class ResourceLoadingThread extends Thread {
	private final ResourceQueue resourceQueue;
	private final ProgressTracker tracker;
	private final HashMap<Enum<?>, ResourceTypeLoader> registeredLoaders;

	public ResourceLoadingThread(ResourceQueue queue, ProgressTracker tracker, HashMap<Enum<?>, ResourceTypeLoader> registeredLoaders)
	{
		this.resourceQueue = queue;
		this.tracker = tracker;
		this.registeredLoaders = registeredLoaders;
		this.setName("Resource loading thread " + this.getId());
	}
	
	public void run()
	{
		UnloadedResource currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
		while(!this.resourceQueue.isDestroyed())
		{
			try {
				if(currentFile == null) {
					Thread.sleep(500);
				} else {
					loadFile(currentFile);
					this.tracker.registerFileLoaded();
				}
			} catch(Exception e) {
				e.printStackTrace();
				FatalExceptionHandler.exitWithErrorMessage("Error occurred during file loading. Message:\n" + e.getMessage());
			}
			currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
		}
	}

	private void loadFile(UnloadedResource currentFile) throws Exception {
		if(!registeredLoaders.containsKey(currentFile.resourceType)) {
			System.err.println("Can't find a loader for resource type \"" + currentFile.resourceType + "\". Has it been registered?");
			return;
		}
		Finalizable resource = registeredLoaders.get(currentFile.resourceType).loadResource(currentFile, resourceQueue);
		if(resource != null) {
			this.resourceQueue.enqueueResourceForFinalization(resource);
		}
	}
}
