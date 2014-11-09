package orre.lxf;

import java.util.HashMap;
import java.util.Set;

import orre.animation.Animatable;
import orre.geom.mesh.Model;
import orre.gl.materials.Material;
import orre.resources.partiallyLoadables.BlueprintMaterial;
import orre.sceneGraph.CoordinateNode;

public class LXFModel implements Model, Animatable {

	public final String name;
	private final HashMap<String, CoordinateNode> partMap = new HashMap<String, CoordinateNode>();
	public final CoordinateNode rootNode;

	public LXFModel(String name, HashMap<BlueprintMaterial, LXFBlueprintPart[]> parts) {
		this.name = name;
		rootNode = new CoordinateNode("LXFModel " + name);
		CoordinateNode partRoot = new CoordinateNode("root");
		rootNode.addChild(partRoot);
		partMap.put("root", partRoot);
		Set<BlueprintMaterial> materials = parts.keySet();
		for(BlueprintMaterial blueprintMaterial : materials) {
			Material material = blueprintMaterial.convertToMaterial();
			partRoot.addChild(material);
			for(LXFBlueprintPart blueprintPart : parts.get(blueprintMaterial)) {
				LXFPart part = blueprintPart.getPartInstance();
				partMap.put(part.partName, part);
				material.addChild(part);
			}
		}
	}

	@Override
	public CoordinateNode getModelPartByName(String name) {
		return partMap.get(name);
	}

	@Override
	public CoordinateNode getRootNode() {
		return rootNode;
	}

	@Override
	public String[] getModelParts() {
		Set<String> modelParts = partMap.keySet();
		return modelParts.toArray(new String[modelParts.size()]);
	}

}
