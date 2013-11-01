package orre.resources.data;

import java.util.ArrayList;
import java.util.HashMap;

import orre.geom.mesh.Mesh3D;
import orre.geom.mesh.ModelPart;
import orre.resources.Finalizable;
import orre.resources.loaders.obj.StoredModelPart;
import orre.resources.partiallyLoadables.PartiallyLoadableModelPart;
import orre.sceneGraph.CoordinateNode;
import orre.sceneGraph.SceneNode;

public class BlueprintModel extends Finalizable {
	private ArrayList<StoredModelPart> topLevelNodeList = new ArrayList<StoredModelPart>();
	private HashMap<String, StoredModelPart> modelParts = new HashMap<String, StoredModelPart>();
	public final String name;
	
	public BlueprintModel(String name)
	{
		this.name = name;
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

	public Mesh3D createMesh() {
		CoordinateNode root = new CoordinateNode();
		Mesh3D mesh = new Mesh3D(root);
		for(StoredModelPart part : this.topLevelNodeList) {
			addChildren(mesh, root, part);
		}
		return mesh;
	}

	private void addChildren(Mesh3D mesh, SceneNode node, StoredModelPart part) {
		ModelPart modelPart = part.createSceneNode();
		node.addChild(modelPart);
		mesh.addPart(part.name, modelPart);
		ArrayList<StoredModelPart> children = part.getChildren();
		for(StoredModelPart child : children) {
			addChildren(mesh, modelPart, child);
		}
	}

	@Override
	public void addToCache() {
		this.destinationCache.addModel(this);
	}

	@Override
	public SceneNode createSceneNode() {
		return null;
	}
}
