package orre.resources;

public interface ResourceTypeLoader {
	public Finalizable loadResource(UnloadedResource source, ResourceQueue queue) throws Exception;
	public Enum<?> getResourceType();
}
