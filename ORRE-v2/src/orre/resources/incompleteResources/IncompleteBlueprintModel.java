package orre.resources.incompleteResources;

import java.util.ArrayList;
import java.util.HashMap;

import orre.geom.mesh.BlueprintModel;
import orre.geom.mesh.Mesh3D;
import orre.geom.mesh.Model;
import orre.geom.mesh.ModelPart;
import orre.resources.IncompleteResourceObject;
import orre.sceneGraph.SceneNode;

public class IncompleteBlueprintModel implements IncompleteResourceObject<BlueprintModel> {
	public ArrayList<IncompleteModelPart> topLevelNodeList = new ArrayList<IncompleteModelPart>();
	public HashMap<String, IncompleteModelPart> modelParts = new HashMap<String, IncompleteModelPart>();
	public final String name;
	
	public IncompleteBlueprintModel(String name)
	{
		this.name = name;
	}

	public void addTopLevelPart(IncompleteModelPart currentPart) {
		this.topLevelNodeList.add(currentPart);
	}

	public void registerPart(IncompleteModelPart part) {
		this.modelParts.put(part.name, part);
	}
//	
//	private Mesh3D createMesh() {
//		Mesh3D mesh = new Mesh3D(name);
//		for(IncompleteModelPart part : this.topLevelNodeList) {
//			addChildren(mesh, mesh.root, part);
//		}
//		return mesh;
//	}
//
//	private void addChildren(Mesh3D mesh, SceneNode node, IncompleteModelPart part) {
//		ModelPart modelPart = part.createSceneNode();
//		node.addChild(modelPart);
//		mesh.addPart(part.name, modelPart);
//		ArrayList<StoredModelPart> children = part.getChildren();
//		for(StoredModelPart child : children) {
//			addChildren(mesh, modelPart, child);
//		}
//	}
}
