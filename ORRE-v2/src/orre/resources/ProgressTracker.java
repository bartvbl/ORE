package orre.resources;

public class ProgressTracker {
	private int totalFilesToLoad = 0;
	private int totalFilesLoaded = 0;
	
	private int totalItemsToLoad = 0;
	private int totalItemsLoaded = 0;
	
	private int totalItemsFinialized = 0;
	
	public double getProgress()
	{
		return 0.3;
	}
	
	public void incrementTotalItemCount(int amount)
	{
		this.totalItemsToLoad += amount;
	}
	
	public void registerItemLoaded()
	{
		
	}
	
	public void addFileToLoad()
	{
		
	}
	
	public void registerFineLoaded()
	{
		
	}
	
	public void registerItemFinalized()
	{
		
	}

	public boolean isFinished() {
		return false;
	}
}
