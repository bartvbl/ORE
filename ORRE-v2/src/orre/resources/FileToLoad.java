package orre.resources;

import java.io.File;

import org.dom4j.Node;

public class FileToLoad {
	public final Node nodeFile;
	private String src;
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
	
	public FileToLoad(String pathPrefix, String src)
	{
		this.nodeFile = null;
		this.fileType = null;
		this.destinationCache = null;
		this.src = src;
		this.pathPrefix = pathPrefix;
	}
	
	public String getPath()
	{
		if(this.nodeFile != null)
		{
			return this.pathPrefix + this.nodeFile.valueOf("@src");
		} else {
			if(this.pathPrefix.endsWith(File.separator))
			{
				return this.pathPrefix + this.src;
			} else {
				return this.pathPrefix + File.separator + this.src;
			}
			
		}
	}
	
	public String toString()
	{
		return "File to load: " + fileType + " located at " + src;
	}
}