package orre.threads;

import java.util.HashMap;

import orre.resources.ResourceCache;
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
	private final HashMap<ResourceType, ResourceTypeLoader> registeredLoaders;

	public ResourceLoadingThread(ResourceQueue queue, ProgressTracker tracker, HashMap<ResourceType, ResourceTypeLoader> registeredLoaders)
	{
		this.resourceQueue = queue;
		this.tracker = tracker;
		this.registeredLoaders = registeredLoaders;
		this.setName("Resource loading thread " + this.getId());
	}
	
	public void run()
	{
		UnloadedResource currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
		while(currentFile != null)
		{
			try {
				loadFile(currentFile);
			} catch(Exception e) {
				e.printStackTrace();
				FatalExceptionHandler.exitWithErrorMessage("Error occurred during file loading. Message:\n" + e.getMessage());
			}
			currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
			this.tracker.registerFileLoaded();
		}
	}

	private void loadFile(UnloadedResource currentFile) throws Exception {
		if(!registeredLoaders.containsKey(currentFile.fileType)) {
			System.err.println("Can't find a loader for resource type \"" + currentFile.fileType + "\". Has it been registered?");
			return;
		}
		System.out.println("Using " + registeredLoaders.get(currentFile.fileType) + " to load " + currentFile);
		Finalizable resource = registeredLoaders.get(currentFile.fileType).loadResource(currentFile, resourceQueue);
		if(resource != null) {
			this.resourceQueue.enqueueResourceForFinalization(resource);
		}
	}
}
