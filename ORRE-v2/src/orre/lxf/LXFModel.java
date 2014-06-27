package orre.lxf;

import java.util.HashMap;
import java.util.Set;

import orre.gl.materials.Material;
import orre.resources.partiallyLoadables.BlueprintMaterial;
import orre.sceneGraph.CoordinateNode;

public class LXFModel {

	public final String name;
	private final HashMap<String, LXFPart> partMap = new HashMap<String, LXFPart>();
	public final CoordinateNode rootNode;

	public LXFModel(String name, HashMap<BlueprintMaterial, LXFBlueprintPart[]> parts) {
		this.name = name;
		rootNode = new CoordinateNode("LXFModel " + name);
		Set<BlueprintMaterial> materials = parts.keySet();
		for(BlueprintMaterial blueprintMaterial : materials) {
			Material material = blueprintMaterial.convertToMaterial();
			rootNode.addChild(material);
			for(LXFBlueprintPart blueprintPart : parts.get(blueprintMaterial)) {
				LXFPart part = blueprintPart.getPartInstance();
				partMap.put(part.partName, part);
				material.addChild(part);
			}
		}
	}

}
