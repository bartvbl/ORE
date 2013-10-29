package orre.geom.mesh;

import java.util.HashMap;

import orre.resources.loaders.obj.StoredModelPart;
import orre.sceneGraph.SceneNode;
import orre.sceneGraph.CoordinateNode;

public class Mesh3D extends CoordinateNode implements SceneNode {
	private HashMap<String, ModelPart> parts = new HashMap<String, ModelPart>();
	
	public ModelPart getModelPartByName(String name) {
		return this.parts.get(name);
	}
	@Override
	public void render() {
		
	}

	public void destroy() {}

	public void addPart(String name, ModelPart createSceneNode) {
		this.parts.put(name, createSceneNode);
	}
}
