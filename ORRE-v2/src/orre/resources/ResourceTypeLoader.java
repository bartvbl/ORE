package orre.resources;

public interface ResourceTypeLoader {
	public IncompleteResourceObject<?> readResource(Resource source) throws Exception;
	public Enum<?> getResourceType();
	public ResourceObject<?> completeResource(IncompleteResourceObject<?> object);
}
