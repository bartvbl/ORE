package orre.geom.mesh;

import java.util.HashMap;

import orre.sceneGraph.CoordinateNode;
import orre.sceneGraph.SceneNode;

public class Mesh3D {
	private HashMap<String, ModelPart> parts = new HashMap<String, ModelPart>();
	public final CoordinateNode root;
	
	public Mesh3D(String name) {
		ModelPart root = new ModelPart(name);
		this.root = root;
		this.parts.put("root", root);
	}
	
	public Mesh3D(String string, CoordinateNode rootNode) {
		this.root = rootNode;
	}

	public ModelPart getModelPartByName(String name) {
		return this.parts.get(name);
	}

	public void destroy() {}

	public void addPart(String name, ModelPart part) {
		this.parts.put(name, part);
	}
}
