package orre.resources;

import java.io.File;

public final class Resource {

	public final Enum<?> type;
	public final String name;
	public final File fileLocation;

	public ResourceObject<?> content;

	private ResourceState state;
	
	public Resource(File location, Enum<?> resourceType, String name) {
		this.fileLocation = location;
		this.type = resourceType;
		this.name = name;
		
		this.state = ResourceState.UNLOADED;
	}
	
	public ResourceState currentState() {
		return state;
	}
	
	public void updateState(ResourceState newState) {
		this.state = newState;
	}
	
	
	
	

}
