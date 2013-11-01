package orre.geom.mesh;

import java.util.HashMap;

import orre.resources.loaders.obj.StoredModelPart;
import orre.sceneGraph.SceneNode;
import orre.sceneGraph.CoordinateNode;

public class Mesh3D {
	private HashMap<String, ModelPart> parts = new HashMap<String, ModelPart>();
	public final CoordinateNode root;
	
	public Mesh3D(CoordinateNode root) {
		this.root = root;
	}
	
	public ModelPart getModelPartByName(String name) {
		return this.parts.get(name);
	}

	public void destroy() {}

	public void addPart(String name, ModelPart part) {
		this.parts.put(name, part);
	}
}
