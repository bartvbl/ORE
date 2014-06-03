package orre.resources;

public final class Resource {

	public final ResourceType type;
	public final String name;
	public final Class<?> contentType;
	public final Object content;
	
	public Resource(ResourceType type, String name, Class<?> contentType, Object content) {
		this.type = type;
		this.name = name;
		this.contentType = contentType;
		this.content = content;
	}

}
