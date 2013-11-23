package orre.geom.mesh;

import java.util.HashMap;

import orre.sceneGraph.CoordinateNode;

public class Mesh3D {
	private HashMap<String, ModelPart> parts = new HashMap<String, ModelPart>();
	public final CoordinateNode root;
	
	public Mesh3D() {
		ModelPart root = new ModelPart();
		this.root = root;
		this.parts.put("root", root);
	}
	
	public ModelPart getModelPartByName(String name) {
		return this.parts.get(name);
	}

	public void destroy() {}

	public void addPart(String name, ModelPart part) {
		this.parts.put(name, part);
	}
}
