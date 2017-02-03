package orre.lxf;

import java.util.HashMap;

import orre.geom.mesh.BlueprintModel;
import orre.geom.mesh.Model;
import orre.resources.ResourceObject;
import orre.resources.incompleteResources.BlueprintMaterial;

public class LXFBlueprintModel implements BlueprintModel, ResourceObject<Model> {

	private final String name;
	private final HashMap<BlueprintMaterial, LXFBlueprintPart[]> parts = new HashMap<BlueprintMaterial, LXFBlueprintPart[]>();
	
	public LXFBlueprintModel(String name) {
		this.name = name;
	}
	
	private LXFModel createModelInstance() {
		return new LXFModel(name, parts);
	}

	public void addMaterialGroup(BlueprintMaterial material, LXFBlueprintPart[] newParts) {
		parts.put(material, newParts);
	}

	@Override
	public Model createInstance() {
		return createModelInstance();
	}

}
