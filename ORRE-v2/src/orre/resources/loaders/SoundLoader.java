package orre.resources.loaders;

import orre.resources.IncompleteResourceObject;
import orre.resources.Resource;
import orre.resources.ResourceObject;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;
import orre.resources.incompleteResources.IncompleteSound;

public class SoundLoader implements ResourceTypeLoader {

	@Override
	public IncompleteResourceObject<IncompleteSound> readResource(Resource source) throws Exception {
		return loadSound(source);
	}

	@Override
	public ResourceType getResourceType() {
		return ResourceType.sound;
	}

	private IncompleteSound loadSound(Resource source) {
		return null;
	}

	@Override
	public ResourceObject<?> completeResource(IncompleteResourceObject<?> object) {
		// TODO Auto-generated method stub
		return null;
	}

}
