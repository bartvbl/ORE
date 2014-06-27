package orre.lxf;

import java.util.ArrayList;
import java.util.HashMap;

import orre.animation.Animatable;
import orre.geom.mesh.BlueprintModel;
import orre.geom.mesh.Model;
import orre.resources.Finalizable;
import orre.resources.Resource;
import orre.resources.ResourceType;
import orre.resources.partiallyLoadables.BlueprintMaterial;
import orre.sceneGraph.CoordinateNode;

public class LXFBlueprintModel implements BlueprintModel, Finalizable {

	private final String name;
	private final HashMap<BlueprintMaterial, LXFBlueprintPart[]> parts = new HashMap<BlueprintMaterial, LXFBlueprintPart[]>();
	
	public LXFBlueprintModel(String name) {
		this.name = name;
	}
	
	@Override
	public Resource finalizeResource() {
		return new Resource(ResourceType.lxfmlModel, this.name, LXFBlueprintModel.class, this);
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
