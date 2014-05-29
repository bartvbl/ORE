package orre.resources;

public interface ResourceTypeLoader {
	public Finalizable loadResource(UnloadedResource source) throws Exception;
}
