package orre.resources;

public final class Resource {

	public final Enum<?> type;
	public final String name;
	public final Class<?> contentType;
	public final Object content;
	
	public Resource(Enum<?> resourceType, String name, Class<?> contentType, Object content) {
		this.type = resourceType;
		this.name = name;
		this.contentType = contentType;
		this.content = content;
	}

}
