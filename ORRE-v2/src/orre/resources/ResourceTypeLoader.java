package orre.resources;

public interface ResourceTypeLoader {
	public Finalizable loadResource(Resource source, ResourceQueue queue) throws Exception;
	public Enum<?> getResourceType();
}
