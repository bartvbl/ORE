package orre.resources;

import java.io.File;


public class UnloadedResource {
	public final File location;
	public final ResourceType fileType;
	public final String name;
	
	public UnloadedResource(ResourceType fileType, File location, String name)
	{
		this.location = location;
		this.fileType = fileType;
		this.name = name;
	}
}