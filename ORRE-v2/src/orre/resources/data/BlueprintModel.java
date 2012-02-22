package orre.resources.data;

import java.util.ArrayList;
import java.util.HashMap;

import orre.resources.Finalizable;
import orre.resources.loaders.obj.StoredModelPart;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;
import orre.sceneGraph.SceneNode;

public class BlueprintModel extends Finalizable {
	private ArrayList<StoredModelPart> topLevelNodeList = new ArrayList<StoredModelPart>();
	private HashMap<String, StoredModelPart> modelParts = new HashMap<String, StoredModelPart>();
	
	public BlueprintModel()
	{
		
	}

	public void addTopLevelPart(StoredModelPart currentPart) {
		this.topLevelNodeList.add(currentPart);
	}

	public void registerPart(StoredModelPart part) {
		this.modelParts.put(part.name, part);
	}
	
	public void linkGeometryPartToModelPart(String partName, PartiallyLoadableModelPart partToLink) {
		StoredModelPart part = this.modelParts.get(partName);
		partToLink.setDestinationPart(part);
	}

	public void finalizeResource() {}

	public SceneNode createSceneNode() {
		return null;
	}

	@Override
	public void addToCache() {}
}
