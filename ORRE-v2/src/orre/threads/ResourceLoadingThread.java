package orre.threads;

import java.util.HashMap;

import orre.resources.ResourceTypeLoader;
import orre.resources.Resource;
import orre.resources.Finalizable;
import orre.resources.ProgressTracker;
import orre.resources.ResourceQueue;
import orre.util.FatalExceptionHandler;

public class ResourceLoadingThread extends Thread {
	private static final long sleepIntervalMillis = 50;
	private final ResourceQueue resourceQueue;
	private final ProgressTracker tracker;
	private final HashMap<Enum<?>, ResourceTypeLoader> registeredLoaders;
	
	private boolean isShutdownRequested = false;

	public ResourceLoadingThread(ResourceQueue queue, ProgressTracker tracker, HashMap<Enum<?>, ResourceTypeLoader> registeredLoaders)
	{
		this.resourceQueue = queue;
		this.tracker = tracker;
		this.registeredLoaders = registeredLoaders;
		this.setName("Resource loading thread " + this.getId());
	}
	
	@Override
	public void run()
	{
		Resource currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
		while(!isShutdownRequested)
		{
			try {
				if(currentFile == null) {
					Thread.sleep(sleepIntervalMillis);
				} else {
					loadFile(currentFile);
					this.tracker.registerFileLoaded();
				}
			} catch(Exception e) {
				e.printStackTrace();
				FatalExceptionHandler.exitWithErrorMessage("Error occurred during file loading of file "+currentFile.name+". Message:\n" + e.getMessage());
			}
			currentFile = this.resourceQueue.getNextEnqueuedFileToLoad();
		}
	}

	private void loadFile(Resource currentFile) throws Exception {
		if(!registeredLoaders.containsKey(currentFile.type)) {
			System.err.println("Can't find a loader for resource type \"" + currentFile.type + "\". Has it been registered?");
			return;
		}
		Finalizable resource;
		try {
			resource = registeredLoaders.get(currentFile.type).loadResource(currentFile, resourceQueue);			
		} catch(Exception e) {
			throw new Exception("An error occurred while loading resource " + currentFile.name + " of type "+currentFile.type+", located at " + currentFile.fileLocation.getAbsolutePath(), e);
		}
		if(resource != null) {
			this.resourceQueue.enqueueResourceForFinalization(resource);
		}
	}
	
	public void requestShutdown() {
		this.isShutdownRequested = true;
	}
}
