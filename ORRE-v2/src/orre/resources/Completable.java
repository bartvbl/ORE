package orre.resources;

public class Completable {

	public final Resource resource;
	public final IncompleteResourceObject<?> incompleteObject;

	public Completable(Resource resource, IncompleteResourceObject<?> incompleteObject) {
		this.resource = resource;
		this.incompleteObject = incompleteObject;
	}

}
