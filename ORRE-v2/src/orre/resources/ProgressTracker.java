package orre.resources;

public class ProgressTracker {
	private int totalFilesToLoad = 0;
	private int totalFilesLoaded = 0;
	
	public double getProgress()
	{
		return 0.3;
	}
	
	public void addFileToLoad()
	{
		
	}
	
	public void registerFileLoaded()
	{
		
	}

	public boolean isFinished() {
		return false;
	}
}
