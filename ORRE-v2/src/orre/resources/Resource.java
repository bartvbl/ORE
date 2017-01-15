package orre.resources;

import java.io.File;

public final class Resource {

	public final Enum<?> type;
	public final String name;
	public final Class<?> contentType;
	public final File fileLocation;

	public ResourceObject<?> content;

	private ResourceState state;
	
	public Resource(File location, Enum<?> resourceType, String name, Class<?> contentType) {
		this.fileLocation = location;
		this.type = resourceType;
		this.name = name;
		this.contentType = contentType;
		
		this.state = ResourceState.UNLOADED;
	}
	
	public ResourceState currentState() {
		return state;
	}
	
	
	
	

}
