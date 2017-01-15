package orre.resources.loaders;

import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceQueue;
import orre.resources.ResourceType;
import orre.resources.ResourceTypeLoader;

public class SoundLoader implements ResourceTypeLoader {

	@Override
	public Finalizable loadResource(Resource source, ResourceQueue queue) throws Exception {
		return loadSound(source);
	}

	@Override
	public ResourceType getResourceType() {
		return ResourceType.sound;
	}

	private Finalizable loadSound(Resource source) {
		return null;
	}

}
