package orre.resources;

import org.dom4j.Node;

public class FileToLoad {
	public final Node nodeFile;
	public final String src;
	public final ResourceFile fileType;
	public final ResourceCache destinationCache;
	public String pathPrefix = "";
	
	public FileToLoad(Node nodeFile, ResourceFile fileType, ResourceCache destinationCache)
	{
		this.nodeFile = nodeFile;
		this.fileType = fileType;
		this.destinationCache = destinationCache;
		this.src = null;
	}
	
	public FileToLoad(ResourceFile fileType, ResourceCache destinationCache, String src)
	{
		this.nodeFile = null;
		this.fileType = fileType;
		this.src = src;
		this.destinationCache = destinationCache;
	}
	
	public String toString()
	{
		return "File to load: " + fileType + " located at " + src;
	}
}