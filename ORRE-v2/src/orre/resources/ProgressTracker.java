package orre.resources;

public class ProgressTracker {
	private int totalFilesToLoad = 0;
	private int totalFilesLoaded = 0;
	
	public double getProgress()
	{
		return (double)totalFilesLoaded / (double)totalFilesToLoad;
	}
	
	public void addFileToLoad()
	{
		this.totalFilesToLoad++;
	}
	
	public synchronized void registerFileLoaded()
	{
		this.totalFilesLoaded++;
	}

	public boolean isFinished() {
		return totalFilesToLoad == totalFilesLoaded;
	}
}
