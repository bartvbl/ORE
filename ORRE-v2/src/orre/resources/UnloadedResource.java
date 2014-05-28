package orre.resources;

import java.io.File;


public class UnloadedResource {
	private String src;
	public final ResourceFile fileType;
	public final ResourceCache destinationCache;
	public String pathPrefix = "";
	public final String name;
	
	public UnloadedResource(ResourceFile fileType, ResourceCache destinationCache, String src, String name)
	{
		this.fileType = fileType;
		this.src = src;
		this.destinationCache = destinationCache;
		this.name = name;
	}
	
	public UnloadedResource(String pathPrefix, String src)
	{
		this.fileType = null;
		this.destinationCache = null;
		this.src = src;
		this.pathPrefix = pathPrefix;
		this.name = "untitled";
	}
	
	public String getPath()
	{
		if(this.pathPrefix.length() == 0)
		{
			return this.src;
		} else {
			return this.pathPrefix + File.separator + this.src;
		}
	}
	
	public String toString()
	{
		return "File to load: " + fileType + " located at " + src;
	}
}