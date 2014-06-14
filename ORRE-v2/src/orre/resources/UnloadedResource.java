package orre.resources;

import java.io.File;


public class UnloadedResource {
	public final File location;
	public final Enum<?> resourceType;
	public final String name;
	
	public UnloadedResource(Enum<?> resourceType, File location, String name)
	{
		this.location = location;
		this.resourceType = resourceType;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "[UnloadedResource " + resourceType + " " + name + "]";
	}
}